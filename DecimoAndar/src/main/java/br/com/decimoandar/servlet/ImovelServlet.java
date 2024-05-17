package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.ImovelDao;
import br.com.decimoandar.model.Imovel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/create-imovel")
@MultipartConfig
public class ImovelServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Imovel imovel = new Imovel();

        imovel.setTipoImovel(request.getParameter("tipoImovel"));
        imovel.setTipoVenda(request.getParameter("tipoVenda"));
        imovel.setValor(request.getParameter("valor"));
        imovel.setEndereco(request.getParameter("endereco"));
        imovel.setNumero(request.getParameter("numero"));
        imovel.setCidade(request.getParameter("cidade"));
        imovel.setUf(request.getParameter("uf"));
        imovel.setCep(request.getParameter("cep"));
        imovel.setNumQuartos(request.getParameter("numQuartos"));
        imovel.setNumBanheiros(request.getParameter("numBanheiros"));
        imovel.setMetrosQuadrados(request.getParameter("metrosQuadrados"));
        imovel.setDescricaoImovel(request.getParameter("descricaoImovel"));

        // Obtendo o ID do usuário do cookie
        int userId = getUserIdFromCookie(request);
        imovel.setUserId(userId);

        ImovelDao imovelDao = new ImovelDao();

        // Criar o imóvel no banco de dados
        int imovelId = imovelDao.createImovel(imovel, userId);

        if (imovelId != 0) {
            // Processar imagens
            List<String> imagePaths = new ArrayList<>();
            for (Part part : request.getParts()) {
                if (part.getName().equals("fotos") && part.getSize() > 0) {
                    String fileName = getFileName(part);
                    String imagePath = "path/to/save/" + fileName;  //lógica para salvar a imagem
                    part.write(imagePath);
                    imagePaths.add(imagePath);
                }
            }

            // Adicionar caminhos das imagens no banco de dados
            if (!imagePaths.isEmpty()) {
                imovelDao.addImagePaths(imovelId, imagePaths);
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
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

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}