package com.mybox.adpaters.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mybox.application.domain.Storage;
import com.mybox.application.domain.constant.Rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(collection = "storage")
@AllArgsConstructor
@NoArgsConstructor
public class StorageEntity {
	
	@Id
	private String id;
	
	private Long all;
	
	private Long current = 0L;
	
	public StorageEntity(String id, Rank rank) {
		this.id = id;
		this.all = rank.getAll();
	}

	public StorageEntity(String id, Long all) {
		this.id = id;
		this.all = all;
	}
	
	public static StorageEntity fromDomain(Storage storage) {
		return new StorageEntity(storage.getId(), storage.getAll());
	}

	public Storage toDomain() {
		return new Storage(id, all, current);
	}

	
}
