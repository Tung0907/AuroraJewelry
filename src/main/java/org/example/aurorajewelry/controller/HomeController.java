package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.model.Product;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

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
