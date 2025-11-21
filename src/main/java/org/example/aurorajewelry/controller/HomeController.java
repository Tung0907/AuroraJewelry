package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "homeController", urlPatterns = {"/product"})
public class HomeController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productDAO.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/views/product.jsp").forward(req, resp);
    }
}
