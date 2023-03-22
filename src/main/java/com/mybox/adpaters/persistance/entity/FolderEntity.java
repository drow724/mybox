package com.mybox.adpaters.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mybox.application.domain.Folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(collection = "folders")
@AllArgsConstructor
@NoArgsConstructor
public class FolderEntity {

	@Id
	private String id;

	private String name;

	private String username;
	
	private String parentId;
	
	public FolderEntity(String name, String username, String parentId) {
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}

	public static FolderEntity fromDomain(Folder folder) {
		return new FolderEntity(folder.getName(), folder.getUsername(), folder.getParentId());
	}

	public Folder toDomain() {
		return new Folder(id, name, username, parentId);
	}
}
