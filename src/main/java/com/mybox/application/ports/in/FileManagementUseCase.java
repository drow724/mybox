package com.mybox.application.ports.in;

import com.mybox.application.domain.File;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileManagementUseCase {
	Mono<File> saveFile(File file);

	Flux<File> findByParentId(String parentId, String username);

	Mono<byte[]> getFile(String id);
}
