package com.kakie.bbs_backend.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("伙伴匹配系统API")
						.version("1.0")
						.description( "系统简介")
						.termsOfService("http://doc.xiaominfo.com")
						.license(new License().name("Apache 2.0")
								.url("http://doc.xiaominfo.com")));
	}
	
}
