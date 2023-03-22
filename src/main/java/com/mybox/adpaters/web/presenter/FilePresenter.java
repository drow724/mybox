package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.File;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilePresenter {

	private String id;
	
	private String name;

	private String username;
	
	private String parentId;
	
	private byte[] file;
	
	public File toDomain() {
		return new File(id, name, username, parentId, file);
	}

	public static FilePresenter fromDomain(File file) {
		return new FilePresenter(file.getId(), file.getName(), file.getUsername(), file.getParentId());
	}
	
	public FilePresenter(String id, String name, String username, String parentId) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}
	
}