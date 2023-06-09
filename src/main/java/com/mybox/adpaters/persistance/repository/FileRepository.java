package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FileEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface FileRepository extends ReactiveMongoRepository<FileEntity, String> {

	Flux<FileEntity> findByParentIdAndUsername(String parentId, String username);

	Mono<FileEntity> findByParentIdAndName(String parentId, String name);
}
