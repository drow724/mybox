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

	private final StorageManagementDBAdapter storageManagementDBAdapter;

	private final ReactiveRedisTemplate<String, File> template;

	@Override
	public Mono<File> saveFile(File file) {
		return storageManagementDBAdapter.valid(file.getUsername(), file.getFile().length).filter(b -> b)
				.flatMap(b -> fileRepository.save(FileEntity.fromDomain(file)).map(FileEntity::toDomain).flatMap(
						f -> template.opsForSet().add("file" + f.getParentId() + f.getUsername(), f).thenReturn(f))
						.map(f -> {
							ObjectMetadata objectMetaData = new ObjectMetadata();
							objectMetaData.setContentType(f.getName().split("\\.")[1]);
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
		return template.opsForSet().members("file" + parentId + username)
				.switchIfEmpty(fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain));
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
