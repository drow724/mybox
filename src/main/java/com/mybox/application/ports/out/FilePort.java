package com.mybox.application.ports.out;

import com.mybox.application.domain.File;

import reactor.core.publisher.Mono;

public interface FilePort {
	Mono<File> saveFile(File file);
}