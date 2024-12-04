package org.oop.dao;

import org.oop.model.AppUser;
import org.oop.service.*;
import org.oop.config.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppUserDAO {

    private static final String SELECT_ALL_USERS = "SELECT * FROM app_user";

    public List<org.oop.model.AppUser> getAllUsers() {
        List<AppUser> users = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                AppUser user = new AppUser();
                user.setId(resultSet.getLong("id"));
                user.setUserType(resultSet.getString("user_type"));
                user.setEmail(resultSet.getString("email"));
                user.setSimulated(resultSet.getBoolean("is_simulated"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setTicketRetrievalRate(resultSet.getInt("ticket_retrieval_rate"));
                user.setTicketReleaseRate(resultSet.getInt("ticket_release_rate"));

                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }
}
