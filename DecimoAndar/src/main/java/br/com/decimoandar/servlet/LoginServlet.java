package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();

        // Verificar as credenciais do usuário no banco de dados
        boolean isValidCredentials = userDao.validateCredentials(email, password);

        if (isValidCredentials) {
            // Criar uma sessão e armazenar informações do usuário
            HttpSession session = request.getSession();
            session.setAttribute("email", email);

            // Redirecionar para a página do perfil do usuário
            response.sendRedirect("/pages/profile.html");
        } else {
            // Enviar resposta de erro para o frontend
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "E-mail ou senha inválidos.");
        }
    }
}
