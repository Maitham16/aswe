package com.example.RecommenderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RecommenderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommenderServiceApplication.class, args);
	}
}