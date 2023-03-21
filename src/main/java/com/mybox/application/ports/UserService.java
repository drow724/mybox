package com.mybox.application.ports;

import org.springframework.stereotype.Service;

import com.mybox.application.domain.User;
import com.mybox.application.ports.in.UserManagementUseCase;
import com.mybox.application.ports.out.UserPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements UserManagementUseCase {
	
	private final UserPort userPort;
	
	@Override
	public Mono<User> join(User user) {
		return userPort.join(user);
	}

	@Override
	public Mono<User> login(User user) {
		return userPort.login(user);
	}

}