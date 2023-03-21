package com.mybox.adpaters.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.user.TokenPresenter;
import com.mybox.application.ports.in.TokenManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenHandler {

	private final TokenManagementUseCase useCase;

	public Mono<ServerResponse> refresh(ServerRequest request) {
		return request.bodyToMono(TokenPresenter.class)
				.flatMap(tokenPresenter -> useCase.refresh(tokenPresenter.toDomain())
						.map(token -> TokenPresenter.fromDomain(token)))
				.flatMap(tokenPresenter -> ServerResponse.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON).body(tokenPresenter, TokenPresenter.class))
				.switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
						.bodyValue(new TokenPresenter()));

	}
}
