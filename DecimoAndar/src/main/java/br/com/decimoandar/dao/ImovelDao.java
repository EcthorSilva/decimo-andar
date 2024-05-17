package br.com.decimoandar.dao;

import br.com.decimoandar.model.Imovel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ImovelDao {

    // Função para criar um novo Imovel no banco de dados
    public int createImovel(Imovel imovel, int userId) {
        int imovelId = 0;
        try {
            String SQL = "INSERT INTO DECIMO_ANDAR.Imovel (tipo, tipoVenda, valor, endereco, numero, cidade, uf, cep, numQuartos, numBanheiros, metrosQuadrados, descricao, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return 0;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, imovel.getTipoImovel());
            preparedStatement.setString(2, imovel.getTipoVenda());
            preparedStatement.setString(3, imovel.getValor());
            preparedStatement.setString(4, imovel.getEndereco());
            preparedStatement.setString(5, imovel.getNumero());
            preparedStatement.setString(6, imovel.getCidade());
            preparedStatement.setString(7, imovel.getUf());
            preparedStatement.setString(8, imovel.getCep());
            preparedStatement.setString(9, imovel.getNumQuartos());
            preparedStatement.setString(10, imovel.getNumBanheiros());
            preparedStatement.setString(11, imovel.getMetrosQuadrados());
            preparedStatement.setString(12, imovel.getDescricaoImovel());
            preparedStatement.setInt(13, userId);

            preparedStatement.executeUpdate();
            System.out.println("Success in insert imovel");

            // Get the generated ID for the Imovel
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    imovelId = generatedKeys.getInt(1);
                    imovel.setIdImovel(imovelId);
                } else {
                    throw new SQLException("Creating imovel failed, no ID obtained.");
                }
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return imovelId;
    }

    // Função para adicionar os caminhos das imagens de um Imovel existente no banco de dados
    public void addImagePaths(int imovelId, List<String> imagePaths) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            for (String imagePath : imagePaths) {
                String imageSQL = "INSERT INTO DECIMO_ANDAR.ImovelImagem (imovel_id, caminho_imagem) VALUES (?, ?)";
                PreparedStatement imageStatement = connection.prepareStatement(imageSQL);
                imageStatement.setInt(1, imovelId);
                imageStatement.setString(2, imagePath);
                imageStatement.execute();
                System.out.println("Success in insert images");
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error during image paths insertion:");
            e.printStackTrace();
        }
    }
}