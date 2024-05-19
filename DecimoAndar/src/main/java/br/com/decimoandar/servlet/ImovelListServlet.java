package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.ImovelDao;
import br.com.decimoandar.model.Imovel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/list-imoveis")
public class ImovelListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUserIdFromCookie(request);
        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autenticado");
            return;
        }

        ImovelDao imovelDao = new ImovelDao();
        List<Imovel> imoveis = imovelDao.getImoveisUsuarioAtivo(userId);

        // Converte a lista de imóveis em JSON
        String json = convertToJson(imoveis);

        // Escreve a resposta como JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
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

    private String convertToJson(List<Imovel> imoveis) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < imoveis.size(); i++) {
            Imovel imovel = imoveis.get(i);
            json.append("{");
            json.append("\"endereco\":\"").append(imovel.getEndereco()).append("\",");
            json.append("\"cep\":\"").append(imovel.getCep()).append("\",");
            json.append("\"imagePaths\":[");
            List<String> imagePaths = imovel.getImagePaths();
            if (imagePaths != null) {
                for (int j = 0; j < imagePaths.size(); j++) {
                    json.append("\"").append(imagePaths.get(j)).append("\"");
                    if (j < imagePaths.size() - 1) {
                        json.append(",");
                    }
                }
            }
            json.append("]}");
            if (i < imoveis.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }
}