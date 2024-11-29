package org.oop.cli;

import org.oop.service.ConfigurationService;
import org.oop.service.SimulationService;

import java.util.Scanner;

public class ConfigCLI {
    private final Scanner scanner;
    private final ConfigurationService configurationService;
    private final SimulationService simulationService;

    public ConfigCLI() {
        this.scanner = new Scanner(System.in);
        this.configurationService = new ConfigurationService();
        this.simulationService = new SimulationService();
    }

    public static void main(String[] args) {
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
                    simulationService.configureSimulation(scanner);
                    break;
                case 3:
                    simulationService.startSimulation();
                    break;
                case 4:
                    viewConfigurations();
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

    private void displayMenu() {
        System.out.println("\n** Welcome to the Event Ticketing System - CLI **");
        String mainMenu = """
                \n---------- Main Menu ----------
                1 - Add Configuration
                2 - Configure Simulation
                3 - Start Simulation
                4 - View Configurations
                0 - Exit
                """;
        System.out.println(mainMenu);
        System.out.print("Enter your choice: ");
    }

    private int getValidatedChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (Exception e) {
                System.out.println("Integer required. Please try again.");
                scanner.nextLine();
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
}
