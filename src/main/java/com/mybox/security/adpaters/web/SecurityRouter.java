package com.mybox.security.adpaters.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.mybox.security.application.ports.out.UserPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityRouter {

	private final ReactiveAuthenticationManager authenticationManager;
	
	private final UserPort userPort;

	@Bean
	public SecurityWebFilterChain secursitygWebFilterChain(ServerHttpSecurity http) {
		return http.exceptionHandling()
				.authenticationEntryPoint(
						(swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
				.accessDeniedHandler(
						(swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
				.and().csrf().disable().formLogin().disable().httpBasic().disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(userPort)
				.authorizeExchange()
				.pathMatchers(HttpMethod.OPTIONS).permitAll().pathMatchers("/login").permitAll().anyExchange()
				.authenticated().and().build();
	}

}
