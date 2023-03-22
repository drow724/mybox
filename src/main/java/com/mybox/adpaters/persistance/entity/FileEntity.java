package com.mybox.adpaters.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mybox.application.domain.File;

import lombok.Getter;

@Getter
@Document(collection = "files")
public class FileEntity {

	@Id
	private String id;

	private String name;

	private String username;
	
	private String parentId;

	public FileEntity(String name, String username, String parentId) {
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}

	public static FileEntity fromDomain(File file) {
		return new FileEntity(file.getName(), file.getUsername(), file.getParentId());
	}

	public File toDomain() {
		return new File(id, name, username, parentId);
	}
}
