package com.mybox.application.ports.in;

import com.mybox.application.domain.User;

import reactor.core.publisher.Mono;

public interface UserManagementUseCase {
	Mono<User> join(User user);
}
