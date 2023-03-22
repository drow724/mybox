package com.mybox.adpaters.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.user.JoinPresenter;
import com.mybox.adpaters.web.presenter.user.LoginPresenter;
import com.mybox.application.ports.in.UserManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

	private final UserManagementUseCase useCase;

	public Mono<ServerResponse> join(ServerRequest request) {
		return request.bodyToMono(JoinPresenter.class).flatMap(
				joinPresenter -> ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(
						useCase.join(joinPresenter.toDomain()).map(JoinPresenter::fromDomain), JoinPresenter.class));
	}

	public Mono<ServerResponse> login(ServerRequest request) {
		return request.bodyToMono(LoginPresenter.class)
				.flatMap(loginPresenter -> ServerResponse.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(useCase.login(loginPresenter.toDomain()).map(user -> LoginPresenter.fromDomain(user)),
								LoginPresenter.class));
	}
}