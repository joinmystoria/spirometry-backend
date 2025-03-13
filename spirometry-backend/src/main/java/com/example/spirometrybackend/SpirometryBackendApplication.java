package com.example.spirometrybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpirometryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpirometryBackendApplication.class, args);
	}

}
