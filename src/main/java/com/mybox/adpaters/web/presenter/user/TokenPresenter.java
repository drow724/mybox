package com.mybox.adpaters.web.presenter.user;

import com.mybox.application.domain.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenPresenter {

	private String accessToken;
	
	private String refreshToken;
	
	public Token toDomain() {
		return new Token(accessToken, refreshToken);
	}

	public TokenPresenter(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public TokenPresenter(Token token) {
		this.accessToken = token.getAccessToken();
		this.refreshToken = token.getRefreshToken();
	}

	public static TokenPresenter fromDomain(Token token) {
		return new TokenPresenter(token);
	}
}