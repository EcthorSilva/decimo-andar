package br.com.decimoandar.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/upload-imagem")
public class UploadImageServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> formItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                List<String> imagePaths = new ArrayList<>();

                // Gerar identificador único para o cadastro
                String uniqueId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

                for (FileItem item : formItems) {
                    // Verifica se o arquivo é uma imagem válida
                    if (!item.isFormField() && isImageFile(item)) {
                        // Criar diretório específico para o cadastro se não existir
                        String uploadPath = "src/main/webapp/imoveisimg/cad" + uniqueId + "/";
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        // Renomear imagens sequencialmente dentro do diretório do cadastro
                        String fileName = "img" + (imagePaths.size() + 1) + getFileExtension(item.getName());
                        String filePath = uploadPath + fileName;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                        // Salvar caminho do arquivo na lista
                        imagePaths.add(filePath);
                    }
                }

                // Você pode agora fazer o que quiser com os caminhos das imagens
                // Por exemplo, salvá-los no banco de dados ou processá-los de outra forma

                response.sendRedirect("/pages/profile.html");
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
}
