package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.UserDao;
import br.com.decimoandar.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUserIdFromCookie(request);

        if (userId != -1) {
            UserDao userDao = new UserDao();
            User user = new User();
            user.setIdUser(userId);

            userDao.deleteUser(user);

            // Remover o cookie de usuário
            Cookie userCookie = new Cookie("userCookie", "");
            userCookie.setMaxAge(0);
            response.addCookie(userCookie);

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
