package com.reprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.reprise.entity")
@EnableJpaRepositories("com.reprise.repository")
@ComponentScan("com.reprise")
public class RepriseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepriseBackendApplication.class, args);
	}

}
