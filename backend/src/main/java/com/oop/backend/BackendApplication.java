package com.oop.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Event Ticketing System", version = "1.0", description = "Event Ticketing System API - IIT OOP Coursework"))
public class BackendApplication {
	public static final String Reset = "\u001B[0m";
	public static final String Green = "\u001B[32m";
	public static final String Yellow = "\u001B[33m";
	public static final String Magenta = "\u001B[35m";
	public static final String Blue = "\u001B[34m";

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println(Blue + "System Processing..." + Reset);
	}
}

/* References:
 * https://www.baeldung.com/spring-boot-start
 * https://quickref.me/java
 * https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
 * https://spring.io/projects/spring-boot
 * https://github.com/spring-projects/spring-boot.git
 * https://www.geeksforgeeks.org/spring/?ref=shm
 */
