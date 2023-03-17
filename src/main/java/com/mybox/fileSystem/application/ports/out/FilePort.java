package com.mybox.fileSystem.application.ports.out;

import com.mybox.fileSystem.application.domain.File;

import reactor.core.publisher.Mono;

public interface FilePort {
	Mono<File> saveFile(File file);
}
