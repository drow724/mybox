package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.File;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilePresenter {

	private String id;

	private final String name;

	public File toDomain() {
		return new File(id, name);
	}

	public static FilePresenter fromDomain(File file) {
		return new FilePresenter(file.getId(), file.getName());
	}
}