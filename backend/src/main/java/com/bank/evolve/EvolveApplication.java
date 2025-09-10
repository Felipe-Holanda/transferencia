package com.bank.evolve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvolveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolveApplication.class, args);
	}

}
