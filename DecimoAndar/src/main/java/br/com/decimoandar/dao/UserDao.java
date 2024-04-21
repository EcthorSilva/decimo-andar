package br.com.decimoandar.dao;

import br.com.decimoandar.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserDao {
    public void createUser(User user){

        try {
            String SQL = "INSERT INTO CADASTRO (NAME, EMAIL, DOC, PASSWORD) VALUES (?, ?, ?, ?)";

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa","sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getDocPfPj());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.execute();

            System.out.println("Success in insert user");

            connection.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
