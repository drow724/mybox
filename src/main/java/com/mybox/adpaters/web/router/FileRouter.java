package com.mybox.adpaters.web.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.handler.FileHandler;

@Configuration
public class FileRouter {

	@Bean
	public RouterFunction<ServerResponse> saveFileRoute(FileHandler handler) {
		return route(POST("/file").and(accept(MediaType.APPLICATION_JSON)), handler::saveFile);
	}
	
	@Bean
	public RouterFunction<ServerResponse> getFileRoute(FileHandler handler) {
		return route(GET("/file/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getFile);
	}
	
	@Bean
	public RouterFunction<ServerResponse> deleteFileRoute(FileHandler handler) {
		return route(DELETE("/file/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteFile);
	}
}
