package org.oop.service;

import org.oop.config.*;
import org.oop.model.*;

import java.sql.*;
import java.util.Scanner;

/**
 * Service class to handle Configuration-related operations.
 */
public class ConfigurationService {

    // Save a new configuration to the database
    public boolean saveConfiguration(Configuration config) {
        String query = "INSERT INTO configuration (total_tickets, ticket_release_rate, customer_retrieval_rate, max_ticket_capacity) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, config.getTotalTickets());
            preparedStatement.setFloat(2, config.getTicketReleaseRate());
            preparedStatement.setFloat(3, config.getCustomerRetrievalRate());
            preparedStatement.setInt(4, config.getMaxTicketCapacity());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
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
            System.err.println("Error retrieving configuration: " + e.getMessage());
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
            System.err.println("Error updating configuration: " + e.getMessage());
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
            System.err.println("Error deleting configuration: " + e.getMessage());
            return false;
        }
    }

    // Add a configuration interactively via CLI
    public void addConfiguration(Scanner scanner) {
        System.out.println("\n** Add Configuration **");

        int totalTickets = getIntInput(scanner, "Enter total tickets: ");
        float ticketReleaseRate = getFloatInput(scanner, "Enter ticket release rate: ");
        float customerRetrievalRate = getFloatInput(scanner, "Enter customer retrieval rate: ");
        int maxTicketCapacity = getIntInput(scanner, "Enter max ticket capacity: ");

        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

        if (saveConfiguration(config)) {
            System.out.println("Configuration added successfully.");
        } else {
            System.out.println("Error adding configuration. Please try again.");
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
            System.err.println("Error fetching configurations: " + e.getMessage());
        }

        return configurations.toString();
    }

    // Helper method to get integer input
    private int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Helper method to get float input
    private float getFloatInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextFloat();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
