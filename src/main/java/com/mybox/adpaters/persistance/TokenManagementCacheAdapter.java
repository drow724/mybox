package com.mybox.adpaters.persistance;

import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.repository.TokenRepository;
import com.mybox.application.domain.Token;
import com.mybox.application.ports.out.TokenPort;
import com.mybox.common.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenManagementCacheAdapter implements TokenPort {

	private final TokenRepository tokenRepository;

	private final JwtProvider jwtProvider;

	@Override
	public Mono<Token> refresh(Token token) {
		return Mono.just(token).filter(t -> jwtProvider.validateToken(t.getRefreshToken()))
				.map(t -> tokenRepository.findById(t.getRefreshToken())).filter(op -> op.isPresent())
				.map(op -> op.get()).filter(t -> !jwtProvider.validateToken(t.getUser().getToken().getAccessToken()))
				.map(t -> new Token(jwtProvider.generateToken(t.getUser()), t.getRefreshToken()));

	}
}