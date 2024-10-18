package com.musungare.BackendForReact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class BackendForReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendForReactApplication.class, args);
	}

}
