package com.oop.backend;

import com.oop.backend.cli.ConfigCLI;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Event Ticketing System", version = "1.0", description = "Event Ticketing System API - IIT OOP Coursework"))
public class BackendApplication {
	// ANSI Color Codes
	public static final String Reset = "\u001B[0m";
	public static final String Green = "\u001B[32m";
	public static final String Yellow = "\u001B[33m";
	public static final String Magenta = "\u001B[35m";
	/*
	References - ANSI Color codes:
	 -Adopted from code at stackoverflow: https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
	 */

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BackendApplication.class, args);
		System.out.println("- - -  Event Ticketing System Application Started  - - -");
		ConfigCLI cli = ctx.getBean(ConfigCLI.class);
		cli.start();
	}
}
