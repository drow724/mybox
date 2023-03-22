package com.mybox.adpaters.persistance;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

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

	private final FileRepository fileRepository;

	private final ReactiveRedisTemplate<String, File> template;

	@Override
	public Mono<File> saveFile(File file) {
		return fileRepository.save(FileEntity.fromDomain(file)).map(FileEntity::toDomain)
				.flatMap(f -> template.opsForSet().add("file" + f.getParentId() + f.getUsername(), f).thenReturn(f));
	}

	@Override
	public Flux<File> findByParentId(String parentId, String username) {
		return template.opsForSet().members("file" + parentId + username)
				.switchIfEmpty(fileRepository.findByParentIdAndUsername(parentId, username).map(FileEntity::toDomain));
	}

}
