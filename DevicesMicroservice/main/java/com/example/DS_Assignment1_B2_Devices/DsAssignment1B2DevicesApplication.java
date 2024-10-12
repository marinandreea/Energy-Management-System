package com.example.DS_Assignment1_B2_Devices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class DsAssignment1B2DevicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsAssignment1B2DevicesApplication.class, args);
	}

}
