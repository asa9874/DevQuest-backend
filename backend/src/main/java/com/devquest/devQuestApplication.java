package com.devquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class devQuestApplication {
 	public static void main(String[] args) {
		SpringApplication.run(devQuestApplication.class, args);
	}

}
