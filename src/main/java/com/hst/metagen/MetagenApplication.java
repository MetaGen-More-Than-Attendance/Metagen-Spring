package com.hst.metagen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@RestControllerAdvice
public class MetagenApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetagenApplication.class, args);
	}

}
