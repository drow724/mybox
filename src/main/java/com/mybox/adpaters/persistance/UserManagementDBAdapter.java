package com.mybox.adpaters.persistance;

import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.TokenEntity;
import com.mybox.adpaters.persistance.entity.UserEntity;
import com.mybox.adpaters.persistance.repository.TokenRepository;
import com.mybox.adpaters.persistance.repository.UserRepository;
import com.mybox.application.domain.User;
import com.mybox.application.ports.out.UserPort;
import com.mybox.common.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserManagementDBAdapter implements UserPort {

	private final TokenRepository tokenRepository;

	private final UserRepository userRepository;

	private final JwtProvider jwtProvider;

	@Override
	public Mono<User> join(User user) {
		return userRepository.findByUsername(user.getUsername()).map(UserEntity::toDomain)
				.switchIfEmpty(userRepository.save(UserEntity.fromDomain(user)).map(UserEntity::toDomain));
	}

	@Override
	public Mono<User> login(User user) {
		return userRepository.findByUsername(user.getUsername()).map(UserEntity::toDomain)
				.doOnNext(u -> u.token(jwtProvider.generateToken(u), jwtProvider.generateRefreshToken(u)))
				.doOnNext(u -> {
					String access = jwtProvider.generateToken(u);
					String refresh = jwtProvider.generateRefreshToken(u);
					TokenEntity s = tokenRepository.save(new TokenEntity(refresh, u));
					u.token(access, refresh);
				});

	}

}