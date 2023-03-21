package com.mybox.adpaters.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mybox.application.domain.Folder;

import lombok.Getter;

@Getter
@Document(collection = "files")
public class FolderEntity {

	@Id
	private String id;

	private String name;

	private String parentId;
	
	public FolderEntity(String id, String name, String parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public static FolderEntity fromDomain(Folder folder) {
		return new FolderEntity(folder.getId(), folder.getName(), folder.getParentId());
	}

	public Folder toDomain() {
		return new Folder(id, name, parentId);
	}
}
