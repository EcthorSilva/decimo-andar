package br.com.decimoandar.servlet;

import br.com.decimoandar.model.Imovel;
import br.com.decimoandar.dao.ImovelDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
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
        // Lista para armazenar os caminhos das imagens
        List<String> imagePaths = new ArrayList<>();

        // Recupera o ID do usuário do cookie
        int imovelId = getImovelIdFromCookie(request);

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

                // Criar o objeto Imovel com a lista de caminhos de imagens
                Imovel imovel = new Imovel(imagePaths);

                ImovelDao imovelDao = new ImovelDao();

                // chama a função addImagePaths e passa como parametro o id do imovel
                imovelDao.addImagePaths(imovelId, imovel.getImagePaths());

            } catch (Exception ex) {
                throw new ServletException("Erro ao processar o formulário", ex);
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

    // recupera o id do imovel dos cookies
    private int getImovelIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("imovelCookie")) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        return -1; // Se o cookie não for encontrado, retorna -1 ou outro valor adequado
    }
}
