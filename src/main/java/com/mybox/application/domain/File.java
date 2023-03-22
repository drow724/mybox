package com.mybox.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class File {

	private String id;
	
	private String name;

	private String username;
	
	private String parentId;
	
	public File(String name, String username, String parentId) {
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}
}
