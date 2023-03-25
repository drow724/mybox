package com.mybox.adpaters.web.presenter.user;

import com.mybox.application.domain.User;
import com.mybox.application.domain.constant.Rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginPresenter {

	private String username;

	private String password;

	private TokenPresenter tokenPresenter;
	
	private Rank rank;
	
	private Long all;
	
	private Long current; 
	
	public User toDomain() {
		return new User(username, password);
	}

	public static LoginPresenter fromDomain(User user) {
		return new LoginPresenter(user.getUsername(), user.getPassword(), new TokenPresenter(user.getToken()), user.getRank(), user.getRank().getAll(), user.getCurrent());
	}

}