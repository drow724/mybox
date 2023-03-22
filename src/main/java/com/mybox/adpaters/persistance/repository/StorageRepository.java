package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.StorageEntity;

@Component
public interface StorageRepository extends ReactiveMongoRepository<StorageEntity, String> {

}