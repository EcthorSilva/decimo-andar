package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.UserDao;
import br.com.decimoandar.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/update-user-data")
public class UpdateUserDataServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonData = stringBuilder.toString();

        // Convertendo JSON para objeto User
        User user = objectMapper.readValue(jsonData, User.class);

        // Obter o ID do usuário do cookie
        int userId = getUserIdFromCookie(request);

        // Verificar se o ID do usuário é válido
        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuário não autenticado");
            return;
        }

        // Definir o ID do usuário no objeto User
        user.setIdUser(userId);

        // Atualizar os dados do usuário
        UserDao userDao = new UserDao();
        userDao.updateUserData(user);

        // Retornar resposta de sucesso
        response.setStatus(HttpServletResponse.SC_OK);
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
        return -1;
    }
}