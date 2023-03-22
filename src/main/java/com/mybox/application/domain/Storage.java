package com.mybox.application.domain;

import com.mybox.application.domain.constant.Rank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Storage {
	
	private String id;
	
	private Long all;
	
	private Long current = 0L;

	public Storage(String id, Long all, Long current) {
		this.id = id;
		this.all = all;
		this.current = current;
	}

	public Storage(String id, Rank rank) {
		this.id = id;
		this.all = rank.getAll();
	}
}
