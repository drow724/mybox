package com.mybox.adpaters.persistance.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mybox.application.domain.User;
import com.mybox.application.domain.constant.Rank;
import com.mybox.application.domain.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

	@Id
	private String id;
	
	private String username;

    private String password;

    private Boolean enabled;
    
    private Rank rank = Rank.NORMAL;
    
    private List<Role> roles;
    
    public UserEntity(String username, String password, Boolean enabled, List<Role> roles) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}
    
	public static UserEntity fromDomain(User user) {
		return new UserEntity(user.getUsername(), user.getPassword(), user.getEnabled(), user.getRoles());
	}
	
	public User toDomain() {
		return new User(id, username, password, enabled, roles, rank);
	}
}
