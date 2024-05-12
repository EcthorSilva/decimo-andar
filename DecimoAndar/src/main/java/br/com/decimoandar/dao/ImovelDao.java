package br.com.decimoandar.dao;

import br.com.decimoandar.model.Imovel;
import br.com.decimoandar.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ImovelDao {

    public void createImovel(Imovel imovel){
        try {
            String SQL = "INSERT INTO DECIMO_ANDAR.IMOVEL (TIPO, LOGRADOURO, METROSQUADRADOS, QUANTIDADEQUARTOS, QUANTIDADEBANHEIROS, CEP, DESCRICAO) VALUES (?, ?, ?, ?, ?, ?, ?)";

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa","sa");

            if (connection != null) {
                System.out.println("Success in database connection");
            } else {
                System.out.println("Failed to connect to the database");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, imovel.getTipoImovel());
            preparedStatement.setString(2, imovel.getEndereco());
            preparedStatement.setDouble(3, Double.parseDouble(imovel.getMetrosQuadrados()));
            preparedStatement.setInt(4, Integer.parseInt(imovel.getNumQuartos()));
            preparedStatement.setInt(5, Integer.parseInt(imovel.getNumBanheiros()));
            preparedStatement.setString(6, imovel.getCep());
            preparedStatement.setString(7, imovel.getDescricaoImovel());

            preparedStatement.execute();
            System.out.println("Success in insert user");

            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
