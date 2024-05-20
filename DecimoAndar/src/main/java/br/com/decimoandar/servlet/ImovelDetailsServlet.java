package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.ImovelDao;
import br.com.decimoandar.model.Imovel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/property-details")
public class ImovelDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            ImovelDao imovelDao = new ImovelDao();
            Imovel imovel = imovelDao.getImovelById(id);

            if (imovel == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Converte o imóvel em formato JSON
            String json = convertToJson(imovel);

            // Escreve a resposta como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String convertToJson(Imovel imovel) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"id\":\"").append(imovel.getIdImovel()).append("\",");
        json.append("\"tipoImovel\":\"").append(imovel.getTipoImovel()).append("\",");
        json.append("\"tipoVenda\":\"").append(imovel.getTipoVenda()).append("\",");
        json.append("\"valor\":\"").append(imovel.getValor()).append("\",");
        json.append("\"endereco\":\"").append(imovel.getEndereco()).append("\",");
        json.append("\"numero\":\"").append(imovel.getNumero()).append("\",");
        json.append("\"cidade\":\"").append(imovel.getCidade()).append("\",");
        json.append("\"uf\":\"").append(imovel.getUf()).append("\",");
        json.append("\"cep\":\"").append(imovel.getCep()).append("\",");
        json.append("\"numQuartos\":\"").append(imovel.getNumQuartos()).append("\",");
        json.append("\"numBanheiros\":\"").append(imovel.getNumBanheiros()).append("\",");
        json.append("\"metrosQuadrados\":\"").append(imovel.getMetrosQuadrados()).append("\",");
        json.append("\"descricaoImovel\":\"").append(imovel.getDescricaoImovel()).append("\",");
        json.append("\"imagePaths\":[");

        // Adiciona os caminhos das imagens
        if (imovel.getImagePaths() != null) {
            for (int i = 0; i < imovel.getImagePaths().size(); i++) {
                json.append("\"").append(imovel.getImagePaths().get(i)).append("\"");
                if (i < imovel.getImagePaths().size() - 1) {
                    json.append(",");
                }
            }
        }

        json.append("]}");
        return json.toString();
    }
}
