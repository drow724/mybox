package com.mybox.adpaters.web.presenter;

import com.mybox.application.domain.File;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilePresenter {

	private String name;

	public File toDomain() {
		return new File(name);
	}

	public static FilePresenter fromDomain(File file) {
		return new FilePresenter(file.getName());
	}
	
	public FilePresenter(String name) {
		this.name = name;
	}
	
}