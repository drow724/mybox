package com.mybox.common.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig implements WebFluxConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
				.resourceChain(false);

		registry.addResourceHandler("/swagger-resources/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/")
				.resourceChain(false);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.consumes(getConsumeContentTypes()).produces(getProduceContentTypes()).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.mybox.adpaters.web.handler")).paths(PathSelectors.any())
				.build();
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumeContentTypes = new HashSet<>();

		consumeContentTypes.add("application/json;charset=UTF-8");
		consumeContentTypes.add("application/x-www-form-urlencoded");

		return consumeContentTypes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produceContentTypes = new HashSet<>();

		produceContentTypes.add("application/json;charset=UTF-8");

		return produceContentTypes;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Gommunity API").description("Gommunity API").build();
	}

}