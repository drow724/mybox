package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.Folder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FolderPresenter {

	private String id;

	private String name;

	private String username;

	private String parentId;

	public Folder toDomain() {
		return new Folder(name, username, parentId);
	}

	public static FolderPresenter fromDomain(Folder folder) {
		return new FolderPresenter(folder.getId(), folder.getName(), folder.getUsername(), folder.getParentId());
	}

	public FolderPresenter(String id, String name, String username, String parentId) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.parentId = parentId;
	}

	public FolderPresenter(Folder folder) {
		this.id = folder.getId();
		this.name = folder.getName();
		this.username = folder.getUsername();
		this.parentId = folder.getParentId();
	}

}