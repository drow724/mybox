package com.mybox.security.adpaters.persistance.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mybox.security.application.domain.Role;

import lombok.Getter;

@Getter
@Document(collection = "user")
public class UserEntity {

	private String id;
	
	private String username;

    private String password;

    private Boolean enabled;

    private List<Role> roles;
}
