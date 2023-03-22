package com.mybox.adpaters.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.presenter.FilePresenter;
import com.mybox.application.ports.in.FileManagementUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FileHandler {

	private final FileManagementUseCase useCase;

	public Mono<ServerResponse> saveFile(ServerRequest request) {
		return request.bodyToMono(FilePresenter.class).flatMap(filePresenter -> ServerResponse
				.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(useCase.saveFile(filePresenter.toDomain()).map(FilePresenter::fromDomain), FilePresenter.class));
	}

	public Mono<ServerResponse> getFile(ServerRequest request) {
		return ServerResponse.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(useCase.getFile(request.pathVariable("id")),
						byte[].class);
	}
}
