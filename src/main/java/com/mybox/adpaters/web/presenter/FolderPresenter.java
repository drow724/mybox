package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.Folder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FolderPresenter {

	private String id;
	
	private String name;

	private String parentId;
	
	public Folder toDomain() {
		return new Folder(id, name, parentId);
	}

	public static FolderPresenter fromDomain(Folder folder) {
		return new FolderPresenter(folder.getId(), folder.getName(), folder.getParentId());
	}
	
	public FolderPresenter(String id, String name, String parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
	
}