package com.mybox.adpaters.persistance;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import com.mybox.application.domain.Token;
import com.mybox.application.domain.User;
import com.mybox.application.ports.out.TokenPort;
import com.mybox.common.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenManagementDBAdapter implements TokenPort {

	private final ReactiveRedisTemplate<String, User> template;

	private final JwtProvider jwtProvider;

	@Override
	public Mono<Token> refresh(Token token) {
		return Mono.just(token).filter(t -> jwtProvider.validateToken(t.getRefreshToken()))
				.flatMap(t -> template.opsForValue().get(t.getRefreshToken()).map(user -> {
					if (!jwtProvider.validateToken(user.getToken().getAccessToken())) {
						template.opsForValue().delete(t.getRefreshToken());
						return null;
					}
					return new Token(jwtProvider.generateToken(user), t.getRefreshToken());
				}));

	}
}