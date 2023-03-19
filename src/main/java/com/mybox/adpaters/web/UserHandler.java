package com.mybox.adpaters.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.UserPresenter;
import com.mybox.application.ports.in.UserManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserHandler {

	private final UserManagementUseCase useCase;

	public Mono<ServerResponse> join(ServerRequest request) {
		return request.bodyToMono(UserPresenter.class)
				.flatMap(userPresenter -> ServerResponse.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(useCase.join(userPresenter.toDomain()).map(UserPresenter::fromDomain),
								UserPresenter.class));
	}
}
