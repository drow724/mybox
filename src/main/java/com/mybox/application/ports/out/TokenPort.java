package com.mybox.application.ports.out;

import org.springframework.security.core.userdetails.UserDetails;

import com.mybox.application.domain.Token;

import reactor.core.publisher.Mono;

public interface TokenPort {

	Mono<Token> refresh(Token token);

}