package br.com.decimoandar.dao;

import br.com.decimoandar.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDao {
    public void createUser(User user){

        try {
            String SQL = "INSERT INTO DECIMO_ANDAR.Usuario (NOME_COMPLETO, EMAIL, CPF_CNPJ, SENHA) VALUES (?, ?, ?, ?)";

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

    public void readUser(User user) {
        try {
            String SQL = "SELECT NOME_COMPLETO, EMAIL, TELEFONE, DATA_NASCIMENTO FROM DECIMO_ANDAR.USUARIO WHERE ID = ?";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, user.getIdUser());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setName(resultSet.getString("NOME_COMPLETO"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setTelefone(resultSet.getString("TELEFONE"));
                user.setDataNascimento(resultSet.getString("DATA_NASCIMENTO"));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateUserData(User user) {
        try {
            String SQL = "UPDATE DECIMO_ANDAR.USUARIO SET TELEFONE = ?, DATA_NASCIMENTO = ? WHERE ID = ?";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, user.getTelefone());
            preparedStatement.setString(2, user.getDataNascimento());
            preparedStatement.setInt(3, user.getIdUser());

            preparedStatement.executeUpdate(); // Use executeUpdate para atualizações

            System.out.println("Success in updating user data");

            connection.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteUser(User user){

        try {
            String SQL = "DELETE FROM DECIMO_ANDAR.Usuario WHERE ID = ?";

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa","sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, user.getIdUser());

            preparedStatement.execute();

            System.out.println("Success in delete user");

            connection.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public boolean emailExists(String email) {
        try {
            String SQL = "SELECT COUNT(*) FROM CADASTRO WHERE EMAIL = ?";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean docExists(String doc) {
        try {
            String SQL = "SELECT COUNT(*) FROM CADASTRO WHERE DOC = ?";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, doc);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public int getUserId(String email, String password) {
        try {
            String SQL = "SELECT ID FROM DECIMO_ANDAR.USUARIO WHERE EMAIL = ? AND SENHA = ?";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("ID");
                System.out.println("ID do usuário recuperado: " + userId); // Adiciona esta linha para imprimir o ID do usuário
                return userId;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }
}
