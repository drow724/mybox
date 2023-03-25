package com.mybox.application.ports.out;

import com.mybox.application.domain.File;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FilePort {
	Mono<File> saveFile(File file);

	Flux<File> findByParentId(String parentId, String username);

	Mono<byte[]> getFile(String id);

	Mono<File> deleteFile(String id, String username);
}
