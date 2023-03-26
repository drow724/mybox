package com.mybox.adpaters.persistance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.mybox.adpaters.persistance.entity.FileEntity;
import com.mybox.adpaters.persistance.repository.FileRepository;
import com.mybox.application.domain.File;
import com.mybox.application.ports.out.FilePort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FileManagementDBAdapter implements FilePort {

	private final AmazonS3 amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;

	private final FileRepository fileRepository;

	private final UserManagementDBAdapter userManagementDBAdapter;

	private final ReactiveRedisTemplate<String, File> fileTemplate;

	@Override
	public Mono<File> saveFile(File file) {
		return fileRepository.findByParentIdAndName(file.getParentId(), file.getName()).map(f -> new File())
				.switchIfEmpty(Mono.just(file)
						.flatMap(f -> userManagementDBAdapter.valid(f.getUsername(), f.getFile().length).thenReturn(f))
						.flatMap(f -> fileRepository.save(FileEntity.fromDomain(file)).map(FileEntity::toDomain))
						.flatMap(f -> fileTemplate.opsForSet().add("file" + f.getParentId() + f.getUsername(), f)
								.thenReturn(f))
						.map(f -> {

							ObjectMetadata objectMetaData = new ObjectMetadata();
							objectMetaData.setContentType(file.getName().split("\\.")[1]);
							objectMetaData.setContentLength(file.getFile().length);

							try (InputStream inputStream = new ByteArrayInputStream(file.getFile())) {
								amazonS3Client
										.putObject(new PutObjectRequest(bucket, f.getId(), inputStream, objectMetaData)
												.withCannedAcl(CannedAccessControlList.PublicRead));
							} catch (IOException e) {
								throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
							}
							return f;
						}));

	}

	@Override
	public Flux<File> findByParentId(String parentId, String username) {
		return fileTemplate.opsForSet().members("file" + parentId + username)
				.switchIfEmpty(fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain));
	}

	@Override
	public Mono<File> deleteFile(String id, String username) {
		return fileRepository.findById(id).flatMap(file -> fileRepository.delete(file).thenReturn(file))
				.flatMap(file -> fileTemplate.opsForSet().pop("file" + file.getParentId() + file.getUsername())
						.filter(data -> !data.getId().equals(file.getId())).flatMap(data -> {
							System.out.println(data.getId());
							return fileTemplate.opsForSet().add("file" + file.getParentId() + file.getUsername(), data)
									.thenReturn(data);
						}).thenReturn(file))
				.flatMap(file -> getFile(file.getId())
						.flatMap(bytes -> userManagementDBAdapter.minusCurrent(username, bytes.length))
						.thenReturn(file))
				.flatMap(file -> {
					amazonS3Client.deleteObject(bucket, file.getId());
					return Mono.just(file.toDomain());
				});
	}

	@Override
	public Mono<byte[]> getFile(String id) {
		S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, id));
		S3ObjectInputStream objectInputStream = o.getObjectContent();

		try {
			return Mono.just(objectInputStream.readAllBytes());
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다.");
		}

	}

}
