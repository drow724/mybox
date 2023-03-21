package com.mybox.adpaters.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.FolderPresenter;
import com.mybox.application.ports.in.FolderManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FolderHandler {

	private final FolderManagementUseCase useCase;

	public Mono<ServerResponse> mkdir(ServerRequest request) {
		return request.bodyToMono(FolderPresenter.class).flatMap(
				folderPresenter -> ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(
						useCase.mkdir(folderPresenter.toDomain()).map(FolderPresenter::fromDomain), FolderPresenter.class));
	}

}