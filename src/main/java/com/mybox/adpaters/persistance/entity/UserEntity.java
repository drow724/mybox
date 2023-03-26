package com.mybox.adpaters.persistance.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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
	@Field(name =  "_id", targetType = FieldType.OBJECT_ID)
	private String id;

	private String username;

	private String password;

	private Boolean enabled;

	@Field(targetType = FieldType.STRING)
	private Rank rank;

	private Long current;

	private List<Role> roles;

	public UserEntity(String username, String password, Boolean enabled, Rank rank, Long current, List<Role> roles) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.rank = rank;
		this.current = current;
		this.roles = roles;
	}

	public static UserEntity fromDomain(User user) {
		return new UserEntity(user.getUsername(), user.getPassword(), user.getEnabled(), user.getRank(),
				user.getCurrent(), user.getRoles());
	}

	public User toDomain() {
		return new User(id, username, password, enabled, roles, rank, current);
	}

	public void addCurrent(Long length) {
		current += length;
	}

	public void minusCurrent(Long length) {
		current -= length;
	}
}
