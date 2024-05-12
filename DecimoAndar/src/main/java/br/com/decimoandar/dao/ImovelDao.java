package br.com.decimoandar.dao;

import br.com.decimoandar.model.Imovel;
import br.com.decimoandar.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ImovelDao {

    public void createImovel(Imovel imovel, int userId){
        try {
            String SQL = "INSERT INTO DECIMO_ANDAR.Imovel (tipo, tipoVenda, valor, endereco, numero, cidade, uf, cep, numQuartos, numBanheiros, metrosQuadrados, descricao, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa","sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

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

            // Definindo o ID do usuário no banco de dados
            preparedStatement.setInt(13, userId);

            preparedStatement.execute();
            System.out.println("Success in insert imovel");

            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}