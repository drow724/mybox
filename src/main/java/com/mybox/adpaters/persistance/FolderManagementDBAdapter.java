package com.mybox.adpaters.persistance;

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
		return folderRepository.findByParentIdAndName(folder.getParentId(), folder.getName()).map(f -> new Folder())
				.switchIfEmpty(folderRepository.save(FolderEntity.fromDomain(folder)).map(FolderEntity::toDomain)
						.flatMap(f -> template.opsForSet().add("folder" + f.getParentId() + f.getUsername(), f)
								.thenReturn(f)));
	}

	@Override
	public Flux<Folder> ls(String parentId, String username) {
		return template.opsForSet().members("folder" + parentId + username)
				.switchIfEmpty(folderRepository.findByparentIdAndUsername(parentId, username).map(f -> f.toDomain())
						.flatMap(file -> template.opsForSet()
								.add("folder" + file.getParentId() + file.getUsername(), file).thenReturn(file)));
	}

}
