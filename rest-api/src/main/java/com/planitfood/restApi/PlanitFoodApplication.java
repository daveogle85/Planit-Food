package com.planitfood.restApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.planitfood.controllers, com.planitfood.data, com.planitfood.auth")
public class PlanitFoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlanitFoodApplication.class, args);
	}
}
