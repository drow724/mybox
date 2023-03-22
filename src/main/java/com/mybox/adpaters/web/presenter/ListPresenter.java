package com.mybox.adpaters.web.presenter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListPresenter {

	private String id;

	private String name;

	private String username;

	private String parentId;

	private String type;

	public ListPresenter(String id, String name, String username, String parentId, String type) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.parentId = parentId;
		this.type = type;
	}

}