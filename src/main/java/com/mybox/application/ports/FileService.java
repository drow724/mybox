package com.mybox.application.ports;

import org.springframework.stereotype.Service;

import com.mybox.application.domain.File;
import com.mybox.application.ports.in.FileManagementUseCase;
import com.mybox.application.ports.out.FilePort;

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
