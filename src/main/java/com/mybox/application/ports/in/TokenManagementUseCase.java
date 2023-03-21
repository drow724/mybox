package com.mybox.application.ports.in;

import com.mybox.application.domain.Token;

import reactor.core.publisher.Mono;

public interface TokenManagementUseCase {

	Mono<Token> refresh(Token domain);
}