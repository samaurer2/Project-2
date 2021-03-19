package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"com"})
@EnableJpaRepositories(basePackages = {"com.repos"})
@EntityScan(basePackages = {"com.entities"})
@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"com/repos"})
//@EntityScan(basePackages = {"com/entities"})
//@ComponentScan(basePackages = {"com"})
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
