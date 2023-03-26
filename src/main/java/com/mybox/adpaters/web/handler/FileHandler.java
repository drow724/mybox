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
		return request.bodyToMono(FilePresenter.class)
				.flatMap(filePresenter -> useCase.saveFile(filePresenter.toDomain()))
				.flatMap(f -> f.getId() != null
						? ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
								.body(Mono.just(f), FilePresenter.class)
						: ServerResponse.status(HttpStatus.BAD_REQUEST).build());
	}

	public Mono<ServerResponse> getFile(ServerRequest request) {
		return useCase.getFile(request.pathVariable("id")).flatMap(bytes -> ServerResponse.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON).contentLength(bytes.length).body(bytes, byte[].class));
	}

	public Mono<ServerResponse> deleteFile(ServerRequest request) {
		return request.principal()
				.flatMap(p -> ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
						.body(useCase.deleteFile(request.pathVariable("id"), p.getName()), FilePresenter.class));
	}
}
