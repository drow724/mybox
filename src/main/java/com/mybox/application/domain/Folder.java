package com.mybox.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {

	private String id;
	
	private String name;

	private String username;
	
	private String parentId;
	
	public Folder(String name, String username, String parentId) {
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}
}
