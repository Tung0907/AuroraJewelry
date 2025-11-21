package org.example.aurorajewelry.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import org.example.aurorajewelry.dao.ProductImageDAO;

@WebServlet("/admin/product/upload-image")
@MultipartConfig(fileSizeThreshold = 1024*1024, maxFileSize = 1024*1024*10, maxRequestSize = 1024*1024*50)
public class UploadProductImageServlet extends HttpServlet {
    private final ProductImageDAO imageDAO = new ProductImageDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pid = req.getParameter("productId");
        if (pid == null || pid.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "productId required");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(pid);
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "productId invalid");
            return;
        }

        Part filePart = req.getPart("imageFile");
        if (filePart == null || filePart.getSize() == 0) {
            resp.sendRedirect(req.getContextPath() + "/admin/product/images?productId=" + productId + "&error=no_file");
            return;
        }

        String submitted = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String ext = "";
        int i = submitted.lastIndexOf('.');
        if (i >= 0) ext = submitted.substring(i);
        String newName = "p" + productId + "_" + System.currentTimeMillis() + ext;

        // nơi lưu: <webapp>/uploads/products/
        String uploads = req.getServletContext().getRealPath("/uploads/products/");
        File dir = new File(uploads);
        if (!dir.exists()) dir.mkdirs();

        Path target = Paths.get(uploads, newName);
        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        String dbPath = "uploads/products/" + newName; // lưu vào DB dùng path này
        imageDAO.insert(productId, dbPath);

        resp.sendRedirect(req.getContextPath() + "/admin/product/images?productId=" + productId);
    }
}
