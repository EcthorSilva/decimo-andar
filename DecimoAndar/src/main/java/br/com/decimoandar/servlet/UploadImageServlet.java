package br.com.decimoandar.servlet;

import br.com.decimoandar.model.Imovel;
import br.com.decimoandar.dao.ImovelDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/upload-imagem")
public class UploadImageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Imovel imovel = new Imovel();
        // Lista para armazenar os caminhos das imagens
        List<String> imagePaths = new ArrayList<>();

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
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    List<FileItem> formItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                    // Gerar identificador único para o cadastro
                    String uniqueId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

                    for (FileItem item : formItems) {
                        if (!item.isFormField() && isImageFile(item)) {
                            String uploadPath = "src/main/webapp/imoveisimg/cad" + uniqueId + "/";
                            File uploadDir = new File(uploadPath);
                            if (!uploadDir.exists()) {
                                uploadDir.mkdirs();
                            }

                            String fileName = "img" + (imagePaths.size() + 1) + getFileExtension(item.getName());
                            String filePath = uploadPath + fileName;
                            File storeFile = new File(filePath);
                            item.write(storeFile);
                            imagePaths.add(filePath);
                        }
                    }

                    // Adicionar caminhos das imagens no banco de dados
                    if (!imagePaths.isEmpty()) {
                        imovelDao.addImagePaths(imovelId, imagePaths);
                    }
                    System.out.println(imagePaths);

                    response.setStatus(HttpServletResponse.SC_CREATED);

                } catch (Exception ex) {
                    throw new ServletException("Erro ao processar o formulário", ex);
                }
            }
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
    private boolean isImageFile(FileItem item) {
        String[] validExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        String fileName = item.getName().toLowerCase();
        for (String ext : validExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
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
