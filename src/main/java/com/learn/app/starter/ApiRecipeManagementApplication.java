package com.learn.app.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.learn.app")
@EnableJpaRepositories("com.learn.app.repository")
@EntityScan("com.learn.app.model")
@EnableTransactionManagement
public class ApiRecipeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRecipeManagementApplication.class, args);
	}

}
