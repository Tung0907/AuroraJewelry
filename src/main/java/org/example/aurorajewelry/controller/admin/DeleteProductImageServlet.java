package org.example.aurorajewelry.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import org.example.aurorajewelry.dao.ProductImageDAO;

@WebServlet("/admin/product/delete-image")
public class DeleteProductImageServlet extends HttpServlet {
    private final ProductImageDAO imageDAO = new ProductImageDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String imageUrl = req.getParameter("imageUrl");
        String pid = req.getParameter("productId");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageDAO.delete(imageUrl);
            String real = req.getServletContext().getRealPath("/") + imageUrl.replace("/", File.separator);
            File f = new File(real);
            if (f.exists()) f.delete();
        }
        String redirect = req.getContextPath() + "/admin/product/images";
        if (pid != null && !pid.isEmpty()) redirect += "?productId=" + pid;
        resp.sendRedirect(redirect);
    }
}
