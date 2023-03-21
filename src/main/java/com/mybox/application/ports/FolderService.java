package com.mybox.application.ports;

import org.springframework.stereotype.Service;

import com.mybox.application.domain.Folder;
import com.mybox.application.ports.in.FolderManagementUseCase;
import com.mybox.application.ports.out.FolderPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FolderService implements FolderManagementUseCase {
	
	private final FolderPort folderPort;
	
	@Override
	public Mono<Folder> mkdir(Folder folder) {
		return folderPort.mkdir(folder);
	}

	@Override
	public Flux<Folder> ls(String parentId, String username) {
		return folderPort.ls(parentId, username);
	}

}