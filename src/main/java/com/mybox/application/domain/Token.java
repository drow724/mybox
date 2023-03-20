package com.mybox.application.domain;

import com.mybox.adpaters.persistance.entity.TokenEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Token {

	private String accessToken;
	
	private String refreshToken;

	public Token(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

}