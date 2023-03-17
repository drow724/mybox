package com.mybox.fileSystem.application.ports.in;

import com.mybox.fileSystem.application.domain.File;

import reactor.core.publisher.Mono;

public interface FileManagementUseCase {
	Mono<File> saveFile(File file);
}
