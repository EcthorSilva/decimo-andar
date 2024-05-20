package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.ImovelDao;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/delete-imovel")
@MultipartConfig
public class imovelDeleteServlet extends HttpServlet {

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imovelIdParam = request.getParameter("id");
        if (imovelIdParam != null) {
            try {
                long imovelId = Long.parseLong(imovelIdParam);
                ImovelDao imovelDao = new ImovelDao();
                imovelDao.deleteImovel(imovelId);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de imóvel inválido.");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de imóvel não fornecido.");
        }
    }
}
