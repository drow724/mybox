package com.mybox.adpaters.web.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.handler.UserHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserRouter {

	@Bean
	public RouterFunction<ServerResponse> joinRoutes(UserHandler handler) {
		return route(POST("/user/join").and(accept(MediaType.APPLICATION_JSON)), handler::join);
	}

	@Bean
	public RouterFunction<ServerResponse> loginRoutes(UserHandler handler) {
		return route(POST("/user/login").and(accept(MediaType.APPLICATION_JSON)), handler::login);
	}

}
