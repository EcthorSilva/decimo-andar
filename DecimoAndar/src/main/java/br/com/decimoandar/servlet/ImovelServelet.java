package br.com.decimoandar.servlet;


import br.com.decimoandar.dao.ImovelDao;
import br.com.decimoandar.model.Imovel;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.tools.jconsole.JConsole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/create-imovel")
public class ImovelServelet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("PASSEI NA SERV!!");
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            stringBuilder.append(line);
        }

        String jsonData = stringBuilder.toString();

        System.out.println(jsonData);


        Imovel imovel = objectMapper.readValue(jsonData, Imovel.class);
        ImovelDao imovelDao = new ImovelDao();

        imovelDao.createImovel(imovel);
        //response.sendRedirect("/pages/login.html");
    }
}
