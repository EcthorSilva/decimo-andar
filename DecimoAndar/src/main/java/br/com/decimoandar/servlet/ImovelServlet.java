package br.com.decimoandar.servlet;

import br.com.decimoandar.dao.ImovelDao;
import br.com.decimoandar.model.Imovel;

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

@WebServlet("/create-imovel")
@MultipartConfig
public class ImovelServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Imovel imovel = new Imovel();
        List<String> imagePaths = new ArrayList<>();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            try {
                List<FileItem> formItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        switch (item.getFieldName()) {
                            case "tipoImovel":
                                imovel.setTipoImovel(item.getString());
                                break;
                            case "tipoVenda":
                                imovel.setTipoVenda(item.getString());
                                break;
                            case "valor":
                                imovel.setValor(item.getString());
                                break;
                            case "endereco":
                                imovel.setEndereco(item.getString());
                                break;
                            case "numero":
                                imovel.setNumero(item.getString());
                                break;
                            case "cidade":
                                imovel.setCidade(item.getString());
                                break;
                            case "uf":
                                imovel.setUf(item.getString());
                                break;
                            case "cep":
                                imovel.setCep(item.getString());
                                break;
                            case "numQuartos":
                                imovel.setNumQuartos(item.getString());
                                break;
                            case "numBanheiros":
                                imovel.setNumBanheiros(item.getString());
                                break;
                            case "metrosQuadrados":
                                imovel.setMetrosQuadrados(item.getString());
                                break;
                            case "descricaoImovel":
                                imovel.setDescricaoImovel(item.getString());
                                break;
                        }
                    }
                }

                int userId = getUserIdFromCookie(request);
                imovel.setUserId(userId);

                ImovelDao imovelDao = new ImovelDao();
                int imovelId = imovelDao.createImovel(imovel, userId);

                if (imovelId != 0) {
                    String uniqueId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
                    String baseUploadPath = getServletContext().getRealPath("/imoveisimg/");
                    File baseUploadDir = new File(baseUploadPath);
                    if (!baseUploadDir.exists()) {
                        baseUploadDir.mkdirs();
                    }

                    String uploadPath = baseUploadPath + "/cad" + uniqueId + "/";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    for (FileItem item : formItems) {
                        if (!item.isFormField() && isImageFile(item)) {
                            String fileName = "img" + (imagePaths.size() + 1) + getFileExtension(item.getName());
                            String filePath = uploadPath + fileName;
                            File storeFile = new File(filePath);
                            item.write(storeFile);
                            imagePaths.add(filePath);
                        }
                    }

                    if (!imagePaths.isEmpty()) {
                        imovelDao.addImagePaths(imovelId, imagePaths);
                    }

                    response.setStatus(HttpServletResponse.SC_CREATED);

                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (Exception ex) {
                throw new ServletException("Erro ao processar o formulário", ex);
            }
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
        return -1;
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