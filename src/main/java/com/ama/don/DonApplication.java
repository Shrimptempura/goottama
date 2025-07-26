package com.ama.don;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ama.don")
public class DonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonApplication.class, args);
	}

}
