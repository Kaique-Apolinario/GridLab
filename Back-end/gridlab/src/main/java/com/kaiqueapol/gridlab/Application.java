package com.kaiqueapol.gridlab;

import org.apache.poi.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "GridLabAPI", version = "1", description = "Documentation of GridLab API"))
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		IOUtils.setByteArrayMaxOverride(1_000_000_000); // Apache's original limit is 100MB, so it will set it to
														// 1GB
		SpringApplication.run(Application.class, args);
	}

}
