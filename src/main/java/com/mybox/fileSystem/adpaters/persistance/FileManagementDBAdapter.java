package com.mybox.fileSystem.adpaters.persistance;

import org.springframework.stereotype.Component;

import com.mybox.fileSystem.adpaters.persistance.entity.FileEntity;
import com.mybox.fileSystem.adpaters.persistance.repository.FileRepository;
import com.mybox.fileSystem.application.domain.File;
import com.mybox.fileSystem.application.ports.out.FilePort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FileManagementDBAdapter implements FilePort {

	private final FileRepository fileRepository;

	@Override
	public Mono<File> saveFile(File file) {
		return fileRepository.save(FileEntity.fromDomain(file)).map(FileEntity::toDomain);
	}

}
