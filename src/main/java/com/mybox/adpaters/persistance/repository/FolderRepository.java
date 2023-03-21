package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FolderEntity;

@Component
public interface FolderRepository extends ReactiveMongoRepository<FolderEntity, String> {

}