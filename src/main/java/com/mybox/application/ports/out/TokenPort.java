package com.mybox.application.ports.out;

import com.mybox.application.domain.Token;

import reactor.core.publisher.Mono;

public interface TokenPort {

	Mono<Token> refresh(Token token);

}