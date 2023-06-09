package com.mybox.adpaters.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.FolderPresenter;
import com.mybox.adpaters.web.presenter.ListPresenter;
import com.mybox.application.ports.in.FileManagementUseCase;
import com.mybox.application.ports.in.FolderManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FolderHandler {

	private final FolderManagementUseCase folderUseCase;

	private final FileManagementUseCase fileUseCase;

	public Mono<ServerResponse> mkdir(ServerRequest request) {
		return request.bodyToMono(FolderPresenter.class)
				.flatMap(folderPresenter -> folderUseCase.mkdir(folderPresenter.toDomain()))
				.flatMap(folder -> folder.getId() != null
						? ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
								.body(Mono.just(folder), FolderPresenter.class)
						: ServerResponse.status(HttpStatus.BAD_REQUEST).build());
	}

	public Mono<ServerResponse> ls(ServerRequest request) {
		return request.principal()
				.flatMap(p -> ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
						folderUseCase.ls(request.pathVariable("parentId"), p.getName())
								.map(f -> new ListPresenter(f.getId(), f.getName(), f.getUsername(), f.getParentId(),
										"folder"))
								.mergeWith(fileUseCase.findByParentId(request.pathVariable("parentId"), p.getName())
										.map(f -> new ListPresenter(f.getId(), f.getName(), f.getUsername(),
												f.getParentId(), "file"))),
						ListPresenter.class));
	}
}