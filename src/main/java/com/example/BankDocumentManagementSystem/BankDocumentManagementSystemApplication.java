package com.example.BankDocumentManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class BankDocumentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankDocumentManagementSystemApplication.class, args);
	}

}
