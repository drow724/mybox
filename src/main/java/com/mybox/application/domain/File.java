package com.mybox.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class File {

	private String name;
	
	public File(String name) {
		this.name = name;
	}
}
