package org.oop.service;

import org.oop.config.*;
import org.oop.model.*;

import java.sql.*;
import java.util.Scanner;

/**
 * Service class to handle Configuration-related operations.
 */
public class ConfigurationService {
    public static final String Red = "\u001B[31m";
    public static final String Reset = "\u001B[0m";

    // Save a new configuration to the database
    public boolean saveConfiguration(Configuration config) {
        // SQL query to insert a new configuration
        String query = "INSERT INTO configuration (total_tickets, ticket_release_rate, customer_retrieval_rate, max_ticket_capacity) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, config.getTotalTickets());
            preparedStatement.setFloat(2, config.getTicketReleaseRate());
            preparedStatement.setFloat(3, config.getCustomerRetrievalRate());
            preparedStatement.setInt(4, config.getMaxTicketCapacity());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(Red + "Error saving configuration: " + e.getMessage() + Reset);
            return false;
        }
    }

    // Retrieve a configuration from the database by ID
    public Configuration getConfiguration(int id) {
        String query = "SELECT * FROM configuration WHERE id = ?";
        Configuration config = null;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                config = new Configuration(
                        resultSet.getInt("total_tickets"),
                        resultSet.getFloat("ticket_release_rate"),
                        resultSet.getFloat("customer_retrieval_rate"),
                        resultSet.getInt("max_ticket_capacity")
                );
                config.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            System.err.println(Red + "Error retrieving configuration: " + e.getMessage() + Reset);
        }

        return config;
    }

    // Update an existing configuration in the database
    public boolean updateConfiguration(Configuration config) {
        String query = "UPDATE configuration SET total_tickets = ?, ticket_release_rate = ?, customer_retrieval_rate = ?, max_ticket_capacity = ? WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, config.getTotalTickets());
            preparedStatement.setFloat(2, config.getTicketReleaseRate());
            preparedStatement.setFloat(3, config.getCustomerRetrievalRate());
            preparedStatement.setInt(4, config.getMaxTicketCapacity());
            preparedStatement.setInt(5, config.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(Red + "Error updating configuration: " + e.getMessage() + Reset);
            return false;
        }
    }

    // Delete a configuration from the database by ID
    public boolean deleteConfiguration(int id) {
        String query = "DELETE FROM configuration WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(Red + "Error deleting configuration: " + e.getMessage() + Reset);
            return false;
        }
    }

    // Add a configuration interactively via CLI
    public void addConfiguration(Scanner scanner) {
        System.out.println("\n-------------- Add Configuration --------------");

        int totalTickets = getIntInput(scanner, "Enter total tickets: ");
        float ticketReleaseRate = getFloatInput(scanner, "Enter ticket release rate: ");
        float customerRetrievalRate = getFloatInput(scanner, "Enter customer retrieval rate: ");

        int maxTicketCapacity;
        while (true) {
            maxTicketCapacity = getIntInput(scanner, "Enter max ticket capacity: ");
            if (maxTicketCapacity > totalTickets) {
                break;
            } else {
                System.out.println(Red + "Max ticket capacity must be greater than total tickets. Please try again." + Reset);
            }
        }

        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

        if (saveConfiguration(config)) {
            System.out.println("Configuration added successfully.");
        } else {
            System.out.println(Red + "Error adding configuration. Please try again." + Reset);
        }
    }

    // Get all configurations as a string
    public String getAllConfigurations() {
        String query = "SELECT * FROM configuration";
        StringBuilder configurations = new StringBuilder();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                configurations.append("ID: ").append(resultSet.getInt("id")).append("\n")
                        .append("Total Tickets: ").append(resultSet.getInt("total_tickets")).append("\n")
                        .append("Ticket Release Rate: ").append(resultSet.getFloat("ticket_release_rate")).append("\n")
                        .append("Customer Retrieval Rate: ").append(resultSet.getFloat("customer_retrieval_rate")).append("\n")
                        .append("Max Ticket Capacity: ").append(resultSet.getInt("max_ticket_capacity")).append("\n\n");
            }
        } catch (SQLException e) {
            System.err.println(Red + "Error fetching configurations: " + e.getMessage() + Reset);
        }
        return configurations.toString();
    }

    // Helper method to get integer input (positive integer only)
    private int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();

                if (input.trim().isEmpty() || !input.matches("\\d+")) {
                    System.out.println(Red + "Input must be a positive integer. Please try again." + Reset);
                } else {
                    int value = Integer.parseInt(input);
                    if (value <= 0) {
                        System.out.println(Red + "Value must be greater than zero. Please try again." + Reset);
                    } else {
                        return value;
                    }
                }
            } catch (Exception e) {
                System.out.println(Red + "Invalid input. Please enter a valid positive integer." + Reset);
            }
        }
    }

    // Helper method to get float input (positive float only)
    private float getFloatInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();

                if (input.trim().isEmpty() || !input.matches("\\d+(\\.\\d+)?")) {
                    System.out.println("Input must be a positive number. Please try again." + Reset);
                } else {
                    float value = Float.parseFloat(input);
                    if (value <= 0) {
                        System.out.println(Red + "Value must be greater than zero. Please try again." + Reset);
                    } else {
                        return value;
                    }
                }
            } catch (Exception e) {
                System.out.println(Red + "Invalid input. Please enter a valid positive number." + Reset);
            }
        }
    }
}
