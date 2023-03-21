package com.mybox.application.ports.in;

import com.mybox.application.domain.File;

import reactor.core.publisher.Mono;

public interface FileManagementUseCase {
	Mono<File> saveFile(File file);
}
