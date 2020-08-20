package com.example.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.function.Function;

@SpringBootApplication
public class BalanceApplication {

	@Bean
	public Function<String, Double> balance() {
		return (lambda) -> {
			Random r = new Random(lambda.hashCode());
			return r.nextDouble() * 100;
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BalanceApplication.class, args);
	}
}
