package com.mybox.adpaters.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.FilePresenter;
import com.mybox.application.ports.in.FileManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserHandler {

	private final FileManagementUseCase useCase;

//	public Mono<ServerResponse> login(ServerRequest request) {
//		return request.bodyToMono(UserPresenter.class)
//				.flatMap(filePresenter -> ServerResponse.status(HttpStatus.CREATED)
//						.contentType(MediaType.APPLICATION_JSON)
//						.body(useCase.saveFile(filePresenter.toDomain()).map(FilePresenter::fromDomain),
//								FilePresenter.class));
//	}
}
