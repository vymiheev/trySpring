package com.example.appsmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Profile(value = {"development", "production"})
public class AppSmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSmartApplication.class, args);
	}

}
