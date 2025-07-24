package com.afterburner.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(apiInfo());
        }

	// API 정보 설정: 제목, 설명, 버전 등을 정의
	private Info apiInfo() {
		return new Info()
			.title("Afterburner API")  // API의 제목
			.description("Afterburner API 스웨거명세서")  // API 설명
			.version("1.0.0");  // API 버전
	}
}