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

	public FileEntity(String name) {
		this.name = name;
	}

	public static FileEntity fromDomain(File file) {
		return new FileEntity(file.getName());
	}

	public File toDomain() {
		return new File(name);
	}
}
