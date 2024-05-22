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
import java.util.List;

@WebServlet("/property")
public class CatalogListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Instancia o DAO de Imovel
            ImovelDao imovelDao = new ImovelDao();

            // Obtém o limite de imóveis para exibição (opcional)
            int limit = 10; // Por exemplo, limitamos a 10 imóveis

            // Obtém a lista de imóveis para exibição do banco de dados
            List<Imovel> imoveis = imovelDao.getImoveisParaExibicao(limit);

            // Converte a lista de imóveis em formato JSON
            String json = convertToJson(imoveis);

            // Define o tipo de conteúdo da resposta como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            // Escreve a resposta como JSON
            out.print(json);
            out.flush();

        } catch (Exception e) {
            // Em caso de erro, retorna erro 500 - Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private String convertToJson(List<Imovel> imoveis) {
        StringBuilder json = new StringBuilder("["); // Inicia um array JSON

        // Percorre a lista de imóveis
        for (int i = 0; i < imoveis.size(); i++) {
            Imovel imovel = imoveis.get(i);
            json.append("{");
            json.append("\"id\":\"").append(imovel.getIdImovel()).append("\",");
            json.append("\"tipoVenda\":\"").append(imovel.getTipoVenda()).append("\",");
            json.append("\"valor\":\"").append(imovel.getValor()).append("\",");
            json.append("\"endereco\":\"").append(imovel.getEndereco()).append("\",");
            json.append("\"imagePaths\":[");

            // Adiciona os caminhos das imagens
            if (imovel.getImagePaths() != null) {
                for (int j = 0; j < imovel.getImagePaths().size(); j++) {
                    json.append("\"").append(imovel.getImagePaths().get(j)).append("\"");
                    if (j < imovel.getImagePaths().size() - 1) {
                        json.append(",");
                    }
                }
            }

            json.append("]}");

            // Adiciona vírgula entre os objetos JSON, exceto no último
            if (i < imoveis.size() - 1) {
                json.append(",");
            }
        }

        json.append("]"); // Fecha o array JSON
        return json.toString();
    }
}