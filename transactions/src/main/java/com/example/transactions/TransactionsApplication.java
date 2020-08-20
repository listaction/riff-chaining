package com.example.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class TransactionsApplication {

	@Bean
	public Function<String, List<Double>> transactions() {
		return (lambda) -> {
			Random r = new Random(lambda.hashCode());
			List<Double> l = new ArrayList();
			for(int i = 0; i < 5; i++ ) {
				l.add(r.nextDouble() * 100);
			}
			return l;
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TransactionsApplication.class, args);
	}
}
