package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.UserEntity;

import reactor.core.publisher.Mono;

@Component
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String>{

	Mono<UserEntity> findByUsername(String username);

}