package com.mybox.fileSystem.application.ports;

import org.springframework.stereotype.Service;

import com.mybox.fileSystem.application.domain.File;
import com.mybox.fileSystem.application.ports.in.FileManagementUseCase;
import com.mybox.fileSystem.application.ports.out.FilePort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FileService implements FileManagementUseCase {
	
	private final FilePort filePort;
	
	public Mono<File> saveFile(File file) {
		return null;
	}
}
