package br.com.decimoandar.dao;

import br.com.decimoandar.model.Imovel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Função para excluir o Imóvel;
    public void deleteImovel(long imovelId) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            String deleteImovelImagemSQL = "DELETE FROM DECIMO_ANDAR.ImovelImagem WHERE imovel_id = ?";
            String deleteImovelSQL = "DELETE FROM DECIMO_ANDAR.Imovel WHERE id = ?";

            PreparedStatement psDeleteImovelImagem = null;
            PreparedStatement psDeleteImovel = null;

            psDeleteImovelImagem = connection.prepareStatement(deleteImovelImagemSQL);
            psDeleteImovelImagem.setLong(1, imovelId);
            psDeleteImovel.executeUpdate();

            psDeleteImovel = connection.prepareStatement(deleteImovelSQL);
            psDeleteImovel.setLong(1, imovelId);
            psDeleteImovel.executeUpdate();

            connection.close();

        } catch (Exception e) {
            System.out.println("Erro ao deleter no Database.");
        }
    }

    // Função para buscar os imóveis de um usuário logado
    // Função para buscar os imóveis de um usuário logado
    public List<Imovel> getImoveisUsuarioAtivo(int userId) {
        List<Imovel> imoveis = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return imoveis;
            }

            String SQL = "SELECT i.id as id_imovel, endereco, cep, caminho_imagem " +
                    "FROM DECIMO_ANDAR.Imovel i " +
                    "LEFT JOIN DECIMO_ANDAR.ImovelImagem ii ON i.id = ii.imovel_id " +
                    "WHERE i.user_id = ? " +
                    "GROUP BY i.id " +
                    "ORDER BY i.id DESC";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idImovel = resultSet.getInt("id_imovel");
                String endereco = resultSet.getString("endereco");
                String cep = resultSet.getString("cep");
                String caminhoImagem = resultSet.getString("caminho_imagem");

                Imovel imovel = new Imovel();
                imovel.setIdImovel(idImovel);
                imovel.setEndereco(endereco);
                imovel.setCep(cep);

                System.out.println(imovel.getIdImovel());

                // Apenas adiciona a primeira imagem encontrada
                if (caminhoImagem != null && imovel.getImagePaths() == null) {
                    List<String> imagePaths = new ArrayList<>();
                    imagePaths.add(caminhoImagem);
                    imovel.setImagePaths(imagePaths);
                }

                imoveis.add(imovel);
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return imoveis;
    }
}