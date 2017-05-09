package com.go.bing.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"com.go.bing"})
@EnableMongoRepositories("com.go.bing.repository")
public class Binggo {

	public static void main(String[] args) {
		SpringApplication.run(Binggo.class, args);
	}

}
