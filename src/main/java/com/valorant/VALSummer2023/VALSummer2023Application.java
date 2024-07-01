package com.valorant.VALSummer2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.valorant")
@SpringBootApplication
public class VALSummer2023Application {

	public static void main(String[] args) {
		SpringApplication.run(VALSummer2023Application.class, args);
	}

}
