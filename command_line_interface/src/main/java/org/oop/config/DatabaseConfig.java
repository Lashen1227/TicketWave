package org.oop.config;

import java.sql.*;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "oop_cw";
    private static final String FULL_URL = URL + DATABASE_NAME + "?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // SQL to create the `configuration` table if it does not exist
    private static final String CREATE_TABLE_QUERY =
            """
            CREATE TABLE IF NOT EXISTS configuration (
                id INT PRIMARY KEY AUTO_INCREMENT,
                total_tickets INT NOT NULL,
                ticket_release_rate FLOAT NOT NULL,
                customer_retrieval_rate FLOAT NOT NULL,
                max_ticket_capacity INT NOT NULL
            )
            """;
    static {
        try {
            createDatabaseIfNotExists();
            createTableIfNotExists();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Get a connection to the database
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FULL_URL, USER, PASSWORD);
    }

    private static void createDatabaseIfNotExists() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
        }
    }

    private static void createTableIfNotExists() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_QUERY);
        }
    }

}
