package com.oop.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "TicketWave - API Documentation", version = "1.0", description = "Real-Time Event Ticketing System."))

public class BackendApplication {
	public static final String Reset = "\u001B[0m";
	public static final String Red = "\u001B[31m";
	public static final String Green = "\u001B[32m";
	public static final String Yellow = "\u001B[33m";
	public static final String Magenta = "\u001B[35m";
	public static final String Blue = "\u001B[34m";
	// Reference: https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println(Blue + "System Processing..." + Reset);
	}
}

// API Documentation: http://localhost:8080/swagger-ui/index.html