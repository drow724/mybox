package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.File;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilePresenter {

	private String name;

	private String username;
	
	private String parentId;
	
	public File toDomain() {
		return new File(name, username, parentId);
	}

	public static FilePresenter fromDomain(File file) {
		return new FilePresenter(file.getName(), file.getUsername(), file.getParentId());
	}
	
	public FilePresenter(String name, String username, String parentId) {
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}
	
}