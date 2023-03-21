package com.mybox.application.ports.in;

import com.mybox.application.domain.Folder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FolderManagementUseCase {
	
	Mono<Folder> mkdir(Folder folder);
	
	Flux<Folder> ls(String parentId, String username);
}
