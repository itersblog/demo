package com.github.demo.user.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import io.swagger.annotations.Api;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
	@Value("${spring.swagger.enable:false}")
	private Boolean enable;

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).enable(enable)
				.apiInfo(new ApiInfoBuilder().title("Demo API").description("This is Demo API").version("1.0").build())
				.select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any())
				.build();
	}
}
