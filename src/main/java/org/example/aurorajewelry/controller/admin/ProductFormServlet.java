package org.example.aurorajewelry.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.dao.CategoryDAO;
import org.example.aurorajewelry.model.Product;

@WebServlet(name="productForm", urlPatterns = {"/admin/product/new", "/admin/product/edit"})
public class ProductFormServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pid = req.getParameter("productId");
        if (pid != null && !pid.isEmpty()) {
            Product p = productDAO.findById(Integer.parseInt(pid));
            req.setAttribute("product", p);
        }
        req.setAttribute("categories", categoryDAO.findAll());
        req.getRequestDispatcher("/views/admin/product_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // handle create/update (simplified) - you can expand validations
        String id = req.getParameter("productId");
        Product p = new Product();
        p.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        p.setProductName(req.getParameter("productName"));
        p.setMaterial(req.getParameter("material"));
        p.setWeight(Double.parseDouble(req.getParameter("weight")));
        p.setGender(req.getParameter("gender"));
        p.setDescription(req.getParameter("description"));
        p.setPrice(new java.math.BigDecimal(req.getParameter("price")));
        if (id == null || id.isEmpty()) {
            productDAO.insert(p);
        } else {
            p.setProductId(Integer.parseInt(id));
            productDAO.update(p);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }
}
