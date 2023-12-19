package com.playdata.eungae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EungaeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EungaeApplication.class, args);
	}

}
