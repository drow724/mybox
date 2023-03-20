package com.mybox.adpaters.web.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mybox.adpaters.web.handler.TokenHandler;

@Configuration
public class TokenRouter {

	@Bean
	public RouterFunction<ServerResponse> refreshRoutes(TokenHandler handler) {
		return route(POST("/token/refresh").and(accept(MediaType.APPLICATION_JSON)), handler::refresh);
	}
	
	
}
