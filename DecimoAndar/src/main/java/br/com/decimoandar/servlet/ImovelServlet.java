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
import java.util.List;

@WebServlet("/create-imovel")
public class ImovelServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonData = stringBuilder.toString();

        // Converter o JSON em objeto Imovel
        Imovel imovel = objectMapper.readValue(jsonData, Imovel.class);

        // Obtendo o ID do usuário do cookie
        int userId = getUserIdFromCookie(request);

        ImovelDao imovelDao = new ImovelDao();

        // Criar o imóvel no banco de dados
        int imovelId = imovelDao.createImovel(imovel, userId);

        // Verificar se o imóvel foi criado com sucesso
        if (imovelId != 0) {

            // Pega o id do anuncio criado e salva em um cookie
            Cookie imovelCookie = new Cookie("imovelCookie", String.valueOf(imovelId));
            imovelCookie.setMaxAge(60); //setting cookie to expiry in 1 min
            response.addCookie(imovelCookie);

            // Enviar resposta de sucesso
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            // Enviar resposta de erro
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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