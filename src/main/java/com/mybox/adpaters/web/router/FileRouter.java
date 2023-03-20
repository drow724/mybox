package com.mybox.adpaters.web.router;

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
	public RouterFunction<ServerResponse> fileRoutes(FileHandler handler) {
		return route(POST("/file").and(accept(MediaType.APPLICATION_JSON)), handler::saveFile);
	}
}
