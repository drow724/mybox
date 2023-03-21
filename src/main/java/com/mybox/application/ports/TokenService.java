package com.mybox.application.ports;

import org.springframework.stereotype.Component;

import com.mybox.application.domain.Token;
import com.mybox.application.ports.in.TokenManagementUseCase;
import com.mybox.application.ports.out.TokenPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenService implements TokenManagementUseCase {

	private final TokenPort tokenPort;

	@Override
	public Mono<Token> refresh(Token token){
		return tokenPort.refresh(token);
	}

}
