package org.oop.cli;

import org.oop.service.*;
import org.oop.dao.AppUserDAO;
import org.oop.model.AppUser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConfigCLI {
    private final Scanner scanner;
    private final ConfigurationService configurationService = new ConfigurationService();
    private final SimulationService simulationService = new SimulationService();

    public ConfigCLI() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("\n*********************************************");
        System.out.println("*** Welcome to the Event Ticketing System ***");
        System.out.println("*********************************************");
        ConfigCLI cli = new ConfigCLI();
        cli.run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getValidatedChoice();

            switch (choice) {
                case 1:
                    configurationService.addConfiguration(scanner);
                    break;
                case 2:
                    viewConfigurations();
                    break;
                case 3:
                    printConfigurations();
                    break;
                case 4:
                    simulationService.startSimulation();
                    break;
                case 5:
                    appUser();
                    break;
                case 0:
                    System.out.println("Exiting the application...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    private final AppUserDAO appUserDAO = new AppUserDAO();

    private void appUser() {
        System.out.println("\n----------- App Users -----------");
        List<AppUser> users = appUserDAO.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found in the database.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private void displayMenu() {
        String mainMenu = """
                \n----------- Main Menu -----------
                1 - Add Configuration Parameters
                2 - View Configuration Parameters
                3 - Print Configuration Parameters
                4 - Start Simulation
                5 - View System Users
                0 - Exit
                """;
        System.out.println(mainMenu);
        System.out.print("Enter your choice: ");
    }

    private int getValidatedChoice() {
        while (true) {
            try {
                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                // Check if the input is non-negative
                if (choice < 0) {
                    System.out.println("Negative numbers are not allowed. Please try again.");
                    continue;
                }

                // Check if the input is an integer and not a float
                if (input.contains(".")) {
                    System.out.println("Floating-point numbers are not allowed. Please try again.");
                    continue;
                }

                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private void viewConfigurations() {
        System.out.println("\n-- Current Configurations --");
        try {
            String configurations = configurationService.getAllConfigurations();
            if (configurations.isEmpty()) {
                System.out.println("No configurations available.");
            } else {
                System.out.println(configurations);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving configurations: " + e.getMessage());
        }
    }

    private void printConfigurations() {
        System.out.println("Saving configurations to a text file...");

        try (FileWriter writer = new FileWriter("configuration_params.txt")) {
            String configurations = configurationService.getAllConfigurations();

            if (configurations.isEmpty()) {
                writer.write("No configurations available.\n");
            } else {
                writer.write(configurations);
            }
            System.out.println("Configurations saved successfully to 'configuration_params.txt'.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
