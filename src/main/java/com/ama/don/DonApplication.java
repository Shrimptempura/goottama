package com.ama.don;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.ama.don")
public class DonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonApplication.class, args);
	}

}
