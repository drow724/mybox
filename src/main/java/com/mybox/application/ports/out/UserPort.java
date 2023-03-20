package com.mybox.application.ports.out;

import com.mybox.application.domain.User;

import reactor.core.publisher.Mono;

public interface UserPort {
	Mono<User> join(User user);

	Mono<User> login(User user);

}