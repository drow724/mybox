package com.mybox.application.ports.out;

import com.mybox.application.domain.Storage;

import reactor.core.publisher.Mono;

public interface StoragePort {
	
	Mono<Storage> saveStorage(Storage storage);

	Mono<Boolean> valid(String id, int length);

}
