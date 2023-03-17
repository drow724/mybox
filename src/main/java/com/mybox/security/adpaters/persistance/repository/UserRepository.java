package com.mybox.security.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.security.adpaters.persistance.entity.UserEntity;

@Component
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String>{

}