package org.example.aurorajewelry.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.model.Product;

@WebServlet("/admin/products")
public class AdminProductController extends HttpServlet {
    private ProductDAO dao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        List<Product> products;
        if (q != null && !q.isBlank()) products = dao.search(q);
        else products = dao.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/views/admin/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.delete(id);
        }
        resp.sendRedirect(req.getContextPath()+"/admin/products");
    }
}
