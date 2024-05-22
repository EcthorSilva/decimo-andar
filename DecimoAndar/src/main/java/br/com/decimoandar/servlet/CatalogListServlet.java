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

@WebServlet("/properties")
public class CatalogListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ImovelDao imovelDao = new ImovelDao();
            List<Imovel> imoveis = imovelDao.getImoveisParaExibicao(9); // Limita a 9 imóveis por vez

            if (imoveis.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Monta a resposta como texto plano
            String responseText = buildResponseText(imoveis);

            // Escreve a resposta
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(responseText);
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private String buildResponseText(List<Imovel> imoveis) {
        StringBuilder responseText = new StringBuilder();

        for (Imovel imovel : imoveis) {
            responseText.append("ID: ").append(imovel.getIdImovel()).append("\n");
            responseText.append("Tipo de Venda: ").append(imovel.getTipoVenda()).append("\n");
            responseText.append("Valor: ").append(imovel.getValor()).append("\n");
            responseText.append("Endereço: ").append(imovel.getEndereco()).append("\n");

            List<String> imagePaths = imovel.getImagePaths();
            if (imagePaths != null && !imagePaths.isEmpty()) {
                responseText.append("Caminho da primeira imagem: ").append(imagePaths.get(0)).append("\n");
            } else {
                responseText.append("Caminho da primeira imagem: Não disponível\n");
            }

            responseText.append("\n");
        }

        return responseText.toString();
    }
}