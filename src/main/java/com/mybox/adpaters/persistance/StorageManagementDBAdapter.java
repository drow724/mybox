package com.mybox.adpaters.persistance;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import com.mybox.adpaters.persistance.entity.StorageEntity;
import com.mybox.adpaters.persistance.repository.StorageRepository;
import com.mybox.application.domain.Storage;
import com.mybox.application.ports.out.StoragePort;
import com.mybox.common.util.StorageCalculatorUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StorageManagementDBAdapter implements StoragePort {

	private final StorageRepository storageRepository;

	private final ReactiveRedisTemplate<String, Storage> template;

	@Override
	public Mono<Storage> saveStorage(Storage storage) {
		return storageRepository.save(StorageEntity.fromDomain(storage)).map(StorageEntity::toDomain)
				.flatMap(s -> template.opsForValue().set(s.getId(), s).thenReturn(s));
	}

	@Override
	public Mono<Boolean> valid(String id, int length) {
		return template.opsForValue().get(id)
				.map(s -> s.getCurrent() + StorageCalculatorUtil.byteToKiloByte(length) <= s.getAll());
	}

}
