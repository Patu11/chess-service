package com.github.patu11.chessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessServiceApplication.class, args);
	}

}
