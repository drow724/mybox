package com.mybox.adpaters.persistance;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FolderEntity;
import com.mybox.adpaters.persistance.repository.FolderRepository;
import com.mybox.application.domain.Folder;
import com.mybox.application.ports.out.FolderPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FolderManagementDBAdapter implements FolderPort {

	private final FolderRepository folderRepository;

	private final ReactiveRedisTemplate<String, Folder> template;

	@Override
	public Mono<Folder> mkdir(Folder folder) {
		return folderRepository.save(FolderEntity.fromDomain(folder)).map(FolderEntity::toDomain)
				.doOnNext(f -> template.opsForValue().set(f.getParentId()+f.getUsername(), f));
	}

	@Override
	public Flux<Folder> ls(String parentId, String username) {
		ReactiveListOperations<String, Folder> ops = template.opsForList();
		return ops.size(username + parentId).flatMapMany(size -> ops.range(parentId + username, 0, size));
	}

}
