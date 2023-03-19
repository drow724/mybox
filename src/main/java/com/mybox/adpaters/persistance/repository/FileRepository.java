package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mybox.adpaters.persistance.entity.FileEntity;

public interface FileRepository extends ReactiveMongoRepository<FileEntity, String> {

}
