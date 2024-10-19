package com.afterburner;

import com.afterburner.oauth.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AfterburnerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfterburnerBackendApplication.class, args);
	}

}
