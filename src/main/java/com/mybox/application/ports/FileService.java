package com.mybox.application.ports;

import org.springframework.stereotype.Service;

import com.mybox.application.domain.File;
import com.mybox.application.ports.in.FileManagementUseCase;
import com.mybox.application.ports.out.FilePort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FileService implements FileManagementUseCase {
	
	private final FilePort filePort;
	
	public Mono<File> saveFile(File file) {
		return filePort.saveFile(file);
	}

	@Override
	public Flux<File> findByParentId(String parentId, String username) {
		return filePort.findByParentId(parentId, username);
	}

	@Override
	public Mono<byte[]> getFile(String id) {
		return filePort.getFile(id);
	}
}
