package com.mybox.adpaters.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import com.mybox.adpaters.persistance.entity.TokenEntity;

public interface TokenRepository extends CrudRepository<TokenEntity, String>{

}