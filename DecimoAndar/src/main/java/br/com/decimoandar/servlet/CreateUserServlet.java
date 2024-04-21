package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.UserDao;
import br.com.decimoandar.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/create-user")
public class CreateUserServlet extends HttpServlet {
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

        User user = objectMapper.readValue(jsonData, User.class);

        UserDao userDao = new UserDao();
        userDao.createUser(user);

        response.sendRedirect("/pages");
    }
}