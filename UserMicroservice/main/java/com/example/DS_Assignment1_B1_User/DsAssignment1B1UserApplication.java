package com.example.DS_Assignment1_B1_User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Import(RestTemplateConfig.class)
public class DsAssignment1B1UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsAssignment1B1UserApplication.class, args);
	}

}
