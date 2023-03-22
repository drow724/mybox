package com.mybox.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

import com.mybox.common.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtProvider jwtProvider;
	
	@Bean
	public ReactiveAuthenticationManager reactiveAuthenticationManager() {
		return new ReactiveAuthenticationManager() {
			@Override
			public Mono<Authentication> authenticate(Authentication authentication) {
				return Mono.just(UsernamePasswordAuthenticationToken.authenticated(authentication.getPrincipal(),
						authentication.getCredentials(), authentication.getAuthorities()));
			}
		};
	}

	@Bean
	public ServerSecurityContextRepository serverSecurityContextRepository() {
		return new ServerSecurityContextRepository() {

			@Override
			public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
				return null;
			}

			@Override
			public Mono<SecurityContext> load(ServerWebExchange exchange) {
				return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
						.filter(header -> header.startsWith("Bearer ")).map(header -> header.substring(7)).filter(header -> jwtProvider.validateToken(header)).flatMap(header -> {
							String username = jwtProvider.getUsernameFromToken(header);
							List<GrantedAuthority> roles = jwtProvider.getRoles(header);
							Authentication auth = new UsernamePasswordAuthenticationToken(username, header, roles);
							return reactiveAuthenticationManager().authenticate(auth).map(SecurityContextImpl::new);
						});
			}
		};
	}

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http.exceptionHandling()
				.authenticationEntryPoint(
						(swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
				.accessDeniedHandler(
						(swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
				.and().csrf().disable().formLogin().disable().httpBasic().disable()
				.authenticationManager(reactiveAuthenticationManager())
				.securityContextRepository(serverSecurityContextRepository()).authorizeExchange()
				.pathMatchers(HttpMethod.OPTIONS).permitAll().pathMatchers("/user/join").permitAll()
				.pathMatchers(HttpMethod.OPTIONS).permitAll().pathMatchers("/user/login").permitAll()
				.pathMatchers(HttpMethod.OPTIONS).permitAll().pathMatchers("/token/refresh").permitAll()
				.anyExchange()
				.authenticated().and().build();
	}

}
