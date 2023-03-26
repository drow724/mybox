package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FolderEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface FolderRepository extends ReactiveMongoRepository<FolderEntity, String> {

	Flux<FolderEntity> findByparentIdAndUsername(String parentId, String username);

	Mono<FolderEntity> findByParentIdAndName(String parentId, String name);

}