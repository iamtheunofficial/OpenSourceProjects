package com.bank;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingApplication {

	@Bean
	public ModelMapper map() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

}
