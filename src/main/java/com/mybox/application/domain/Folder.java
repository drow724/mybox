package com.mybox.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Folder {

	private String id;
	
	private String name;

	private String parentId;
	
	public Folder(String id, String name, String parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
}
