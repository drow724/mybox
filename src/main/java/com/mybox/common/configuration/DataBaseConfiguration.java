package com.mybox.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.mybox.application.domain.Folder;
import com.mybox.application.domain.User;
import com.ulisesbocchio.jasyptspringboot.annotation.ConditionalOnMissingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import redis.embedded.RedisServer;

@Configuration
@RequiredArgsConstructor
@ConditionalOnSingleCandidate(ReactiveRedisConnectionFactory.class)
public class DataBaseConfiguration {

//	@Value("${spring.data.mongodb.host}")
//	private String host;
//
//	@Value("${spring.data.mongodb.port}")
//	private int port;
//
//	@Bean
//	@Profile("local")
//	public void embeddedMongo() throws IOException {
//
//		MongodConfig mongodConfig = MongodConfig.builder().version(Version.Main.PRODUCTION)
//				.net(new Net(host, port, Network.localhostIsIPv6())).build();
//
//		MongodStarter starter = MongodStarter.getDefaultInstance();
//		MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
//		mongodExecutable.start();
//
//	}

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.host}")
	private String host;

	@Bean
	@Profile({"local","test"})
	public ReactiveRedisConnectionFactory redisStandaloneConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
		return new LettuceConnectionFactory(configuration);
	}

	@Bean
	@Profile({"dev","uat","prod"})
	public ReactiveRedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	public ReactiveRedisTemplate<String, User> userRedisTemplate(
			ReactiveRedisConnectionFactory connectionFactory) {

		Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<User>(User.class);
		RedisSerializationContextBuilder<String, User> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, User> serializationContext = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}

	@Bean
	public ReactiveRedisTemplate<String, Folder> reactiveFolderRedisTemplate(
			ReactiveRedisConnectionFactory connectionFactory) {

		Jackson2JsonRedisSerializer<Folder> serializer = new Jackson2JsonRedisSerializer<Folder>(Folder.class);
		RedisSerializationContextBuilder<String, Folder> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, Folder> serializationContext = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}
	
	@Bean
	@Profile({ "local", "test" })
	public EmbeddedRedisConfiguration embeddedRedisConfiguration() {
		return new EmbeddedRedisConfiguration(port);
	}

	static class EmbeddedRedisConfiguration {

		private final RedisServer redisServer;

		public EmbeddedRedisConfiguration(int port) {
			this.redisServer = new RedisServer(port);
		}

		@PostConstruct
		public void redisServer() {
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
}