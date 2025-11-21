package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.ProductDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="adminProduct", urlPatterns = {"/admin/products"})
public class AdminProductController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // list
        req.setAttribute("products", productDAO.findAll());
        req.getRequestDispatcher("/views/admin/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean ok = productDAO.delete(id);
            req.setAttribute("msg", ok ? "Xóa thành công" : "Xóa thất bại (kiểm tra ràng buộc)");
        }
        doGet(req, resp);
    }
}
