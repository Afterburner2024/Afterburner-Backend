package com.afterburner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AfterburnerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfterburnerBackendApplication.class, args);
	}

}
