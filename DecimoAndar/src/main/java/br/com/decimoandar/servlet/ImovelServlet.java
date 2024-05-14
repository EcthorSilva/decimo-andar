package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.ImovelDao;
import br.com.decimoandar.model.Imovel;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/create-imovel")
public class ImovelServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonData = stringBuilder.toString();

        Imovel imovel = objectMapper.readValue(jsonData, Imovel.class);

        // Obtendo o ID do usuário do cookie
        int userId = getUserIdFromCookie(request);

        ImovelDao imovelDao = new ImovelDao();

        // Passando o ID do usuário para o método createImovel
        imovelDao.createImovel(imovel, userId);
    }

    private int getUserIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userCookie")) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        return -1; // Se o cookie não for encontrado, retorna -1 ou outro valor adequado
    }
}