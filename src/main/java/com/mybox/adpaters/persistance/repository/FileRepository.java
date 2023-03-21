package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FileEntity;

@Component
public interface FileRepository extends ReactiveMongoRepository<FileEntity, String> {

}
