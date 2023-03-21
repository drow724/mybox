package com.mybox.adpaters.web.presenter.user;

import java.util.List;

import com.mybox.application.domain.User;
import com.mybox.application.domain.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinPresenter {

	private String username;

    private String password;

    @Getter @Setter
    private Boolean enabled = Boolean.TRUE;

    @Getter @Setter
    private List<Role> roles;

	public User toDomain() {
		return new User(username, password, enabled, roles);
	}

	public static JoinPresenter fromDomain(User	user) {
		return new JoinPresenter(user.getUsername(), user.getPassword(), user.getEnabled(), user.getRoles());
	}
}