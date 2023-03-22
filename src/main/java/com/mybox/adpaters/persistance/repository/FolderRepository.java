package com.mybox.adpaters.persistance.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.FolderEntity;
import com.mybox.application.domain.Folder;

import reactor.core.publisher.Flux;

@Component
public interface FolderRepository extends ReactiveMongoRepository<FolderEntity, String> {

	Flux<Folder> findByparentIdAndUsername(String parentId, String username);

}