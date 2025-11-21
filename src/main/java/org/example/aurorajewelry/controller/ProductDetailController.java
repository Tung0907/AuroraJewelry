package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.dao.ProductImageDAO;
import org.example.aurorajewelry.dao.ProductVariantDAO;
import org.example.aurorajewelry.model.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "productDetail", urlPatterns = {"/product-detail"})
public class ProductDetailController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private ProductImageDAO imageDAO = new ProductImageDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idS = req.getParameter("id");
        if (idS == null) { resp.sendRedirect(req.getContextPath()+"/product"); return; }
        int id = Integer.parseInt(idS);
        Product p = productDAO.findById(id);
        if (p == null) { resp.sendRedirect(req.getContextPath()+"/product"); return; }
        req.setAttribute("product", p);
        req.setAttribute("images", imageDAO.findByProductId(id));
        req.setAttribute("variants", variantDAO.findByProductId(id));
        req.getRequestDispatcher("/views/product_detail.jsp").forward(req, resp);
    }
}
