package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FileEntity;
import com.mybox.application.domain.File;

import reactor.core.publisher.Flux;

@Component
public interface FileRepository extends ReactiveMongoRepository<FileEntity, String> {

	Flux<File> findByParentIdAndUsername(String parentId, String username);

}
