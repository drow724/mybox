package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPresenter {

	private String id;

	private final String name;

	public User toDomain() {
		//return new User(id, name);
		return null;
	}

	public static UserPresenter fromDomain(User	user) {
		//return new UserPresenter(user.getId(), user.getName());
		return null;
	}
}