package br.com.decimoandar.dao;

import br.com.decimoandar.model.Imovel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

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

            System.out.println(imovelId);

            PreparedStatement psDeleteImovelImagem = connection.prepareStatement(deleteImovelImagemSQL);
            psDeleteImovelImagem.setLong(1, imovelId);
            psDeleteImovelImagem.execute();

            System.out.println(imovelId);

            PreparedStatement psDeleteImovel = connection.prepareStatement(deleteImovelSQL);
            psDeleteImovel.setLong(1, imovelId);
            psDeleteImovel.execute();

            connection.close();

        } catch (Exception e) {
            System.out.println("Erro ao deleter no Database.");
        }
    }

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

    // Função para buscar um imóvel pelo ID
    public Imovel getImovelById(int imovelId) {
        Imovel imovel = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return null;
            }

            String SQL = "SELECT i.id AS id_imovel, i.tipo AS tipo_imovel, i.tipoVenda, i.valor, i.endereco, i.numero, i.cidade, i.uf, i.cep, " +
                    "i.numQuartos, i.numBanheiros, i.metrosQuadrados, i.descricao, i.USER_ID, ii.caminho_imagem " +
                    "FROM DECIMO_ANDAR.Imovel i " +
                    "LEFT JOIN DECIMO_ANDAR.ImovelImagem ii ON i.id = ii.imovel_id " +
                    "WHERE i.id = ?";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, imovelId);
            resultSet = preparedStatement.executeQuery();

            List<String> imagePaths = new ArrayList<>();

            while (resultSet.next()) {
                if (imovel == null) {
                    imovel = new Imovel();
                    imovel.setIdImovel(resultSet.getInt("id_imovel"));
                    imovel.setTipoImovel(resultSet.getString("tipo_imovel"));
                    imovel.setTipoVenda(resultSet.getString("tipoVenda"));
                    imovel.setValor(resultSet.getString("valor"));
                    imovel.setEndereco(resultSet.getString("endereco"));
                    imovel.setNumero(resultSet.getString("numero"));
                    imovel.setCidade(resultSet.getString("cidade"));
                    imovel.setUf(resultSet.getString("uf"));
                    imovel.setCep(resultSet.getString("cep"));
                    imovel.setNumQuartos(resultSet.getString("numQuartos"));
                    imovel.setNumBanheiros(resultSet.getString("numBanheiros"));
                    imovel.setMetrosQuadrados(resultSet.getString("metrosQuadrados"));
                    imovel.setDescricaoImovel(resultSet.getString("descricao"));
                    imovel.setUserId(resultSet.getInt("USER_ID"));
                }

                String caminhoImagem = resultSet.getString("caminho_imagem");
                if (caminhoImagem != null) {
                    imagePaths.add(caminhoImagem);
                }
            }

            if (imovel != null) {
                imovel.setImagePaths(imagePaths);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return imovel;
    }
    public List<Imovel> getImoveisParaExibicao(int limit) {
        List<Imovel> imoveis = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return imoveis;
            }

            String SQL = "SELECT i.id as id_imovel, i.tipoVenda, i.valor, i.endereco, ii.caminho_imagem " +
                    "FROM DECIMO_ANDAR.Imovel i " +
                    "LEFT JOIN (SELECT imovel_id, caminho_imagem FROM DECIMO_ANDAR.ImovelImagem GROUP BY imovel_id) ii " +
                    "ON i.id = ii.imovel_id " +
                    "GROUP BY i.id " +
                    "LIMIT ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idImovel = resultSet.getInt("id_imovel");
                String tipoVenda = resultSet.getString("tipoVenda");
                String valor = resultSet.getString("valor");
                String endereco = resultSet.getString("endereco");
                String caminhoImagem = resultSet.getString("caminho_imagem");

                Imovel imovel = new Imovel();
                imovel.setIdImovel(idImovel);
                imovel.setTipoVenda(tipoVenda);
                imovel.setValor(valor);
                imovel.setEndereco(endereco);

                // Adiciona apenas a primeira imagem encontrada
                if (caminhoImagem != null) {
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
    public String getAdvertiserPhoneNumber(int userId) {
        String phoneNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return null;
            }

            String SQL = "SELECT telefone FROM DECIMO_ANDAR.Usuario WHERE id = ?";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                phoneNumber = resultSet.getString("telefone");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return phoneNumber;
    }

}