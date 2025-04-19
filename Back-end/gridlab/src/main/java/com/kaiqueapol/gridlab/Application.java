package com.kaiqueapol.gridlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "GridLabAPI", version = "1", description = "Documentation of GridLab API"))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
