package com.mybox.adpaters.persistance;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.UserEntity;
import com.mybox.adpaters.persistance.repository.UserRepository;
import com.mybox.application.domain.Folder;
import com.mybox.application.domain.Storage;
import com.mybox.application.domain.User;
import com.mybox.application.ports.out.UserPort;
import com.mybox.common.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserManagementDBAdapter implements UserPort {

	private final ReactiveRedisTemplate<String, User> template;

	private final UserRepository userRepository;

	private final StorageManagementDBAdapter storageManagementDBAdapter;

	private final FolderManagementDBAdapter folderManagementDBAdapter;

	private final JwtProvider jwtProvider;

	@Override
	public Mono<User> join(User user) {
		return userRepository.findByUsername(user.getUsername()).map(UserEntity::toDomain)
				.switchIfEmpty(userRepository.save(UserEntity.fromDomain(user)).map(UserEntity::toDomain))
				.flatMap(u -> folderManagementDBAdapter.mkdir(new Folder("/", u.getUsername(), "root")).thenReturn(u))
				.flatMap(u -> storageManagementDBAdapter.saveStorage(new Storage(u.getUsername(), u.getRank()))
						.thenReturn(u));
	}

	@Override
	public Mono<User> login(User user) {
		return userRepository.findByUsername(user.getUsername()).map(UserEntity::toDomain)
				.doOnNext(u -> u.token(jwtProvider.generateToken(u), jwtProvider.generateRefreshToken(u)))
				.doOnNext(u -> {
					String access = jwtProvider.generateToken(u);
					String refresh = jwtProvider.generateRefreshToken(u);
					template.opsForValue().set(refresh, u).then();
					u.token(access, refresh);
				});
	}

}