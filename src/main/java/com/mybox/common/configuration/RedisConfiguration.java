package com.mybox.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.mybox.application.domain.File;
import com.mybox.application.domain.Folder;
import com.mybox.application.domain.User;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

@Configuration
public class RedisConfiguration {
	
	private RedisServer redisServer;
	
	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.host}")
	private String host;
	
	@Bean
	@Primary
	@Profile({"local","test"})
	public ReactiveRedisConnectionFactory redisStandaloneConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
		return new LettuceConnectionFactory(configuration);
	}
	
	@Bean
    public ReactiveRedisTemplate<String, Folder> reactiveFolderRedisTemplate(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<Folder> serializer = new Jackson2JsonRedisSerializer<>(Folder.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Folder> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Folder> context = builder.value(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
	
	@Bean
    public ReactiveRedisTemplate<String, User> reactiveUserRedisTemplate(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, User> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, User> context = builder.value(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
	
	@Bean
    public ReactiveRedisTemplate<String, File> reactiveFileRedisTemplate(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<File> serializer = new Jackson2JsonRedisSerializer<>(File.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, File> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, File> context = builder.value(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
	
	@PostConstruct
	public void redisServer() {
		redisServer = new RedisServer(port);
		if (redisServer.isActive()) {
			redisServer.stop();
		}
		redisServer.start();
	}
	
	@PreDestroy
	public void preDestroy() {
		redisServer.stop();
	}
}
