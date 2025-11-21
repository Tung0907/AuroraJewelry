package org.example.aurorajewelry.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.example.aurorajewelry.dao.ProductImageDAO;

@WebServlet("/admin/product/images")
public class ProductImageManagerServlet extends HttpServlet {
    private final ProductImageDAO imageDAO = new ProductImageDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pid = req.getParameter("productId");
        int productId = 0;
        if (pid != null && !pid.isEmpty()) {
            try { productId = Integer.parseInt(pid); } catch (NumberFormatException ignored) {}
        }
        req.setAttribute("productId", productId);
        req.setAttribute("images", imageDAO.findByProductId(productId));
        req.getRequestDispatcher("/views/admin/product_images.jsp").forward(req, resp);
    }
}
