package com.abdur.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TestCasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCasesApplication.class, args);
	}

}
