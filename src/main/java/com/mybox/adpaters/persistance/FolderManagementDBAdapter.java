package com.mybox.adpaters.persistance;

import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FolderEntity;
import com.mybox.adpaters.persistance.repository.FolderRepository;
import com.mybox.application.domain.Folder;
import com.mybox.application.ports.out.FolderPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FolderManagementDBAdapter implements FolderPort {

	private final FolderRepository folderRepository;

	@Override
	public Mono<Folder> mkdir(Folder folder) {
		return folderRepository.save(FolderEntity.fromDomain(folder)).map(FolderEntity::toDomain);
	}

}
