package com.mybox.common.configuration;

import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
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
}