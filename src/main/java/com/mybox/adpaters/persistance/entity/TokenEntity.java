package com.mybox.adpaters.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.mybox.application.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@RedisHash(value =  "user", timeToLive = 2280L)
@AllArgsConstructor
public class TokenEntity {
	
	@Id
	private String refreshToken;
	
	private User user;
}