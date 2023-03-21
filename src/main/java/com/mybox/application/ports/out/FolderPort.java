package com.mybox.application.ports.out;

import com.mybox.application.domain.Folder;

import reactor.core.publisher.Mono;

public interface FolderPort {

	Mono<Folder> mkdir(Folder folder);

}