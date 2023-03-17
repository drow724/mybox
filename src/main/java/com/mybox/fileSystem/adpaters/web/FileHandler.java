package com.mybox.fileSystem.adpaters.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.fileSystem.adpaters.web.presenter.FilePresenter;
import com.mybox.fileSystem.application.ports.in.FileManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileHandler {

	private final FileManagementUseCase useCase;

	public Mono<ServerResponse> saveFile(ServerRequest request) {
		return request.bodyToMono(FilePresenter.class)
				.flatMap(filePresenter -> ServerResponse.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(useCase.saveFile(filePresenter.toDomain()).map(FilePresenter::fromDomain),
								FilePresenter.class));
	}
}
