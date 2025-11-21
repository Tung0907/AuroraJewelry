package org.example.aurorajewelry.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import org.example.aurorajewelry.dao.CategoryDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

    @WebServlet(name="adminCategories", urlPatterns={"/admin/categories"})
    public class AdminCategoryController extends HttpServlet {
        private CategoryDAO categoryDAO = new CategoryDAO();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Map<String,Object>> categories = categoryDAO.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/categories.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String action = req.getParameter("action");
            if ("create".equals(action)) {
                String name = req.getParameter("name");
                String desc = req.getParameter("desc");
                categoryDAO.insert(name, desc);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                String desc = req.getParameter("desc");
                categoryDAO.update(id, name, desc);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                categoryDAO.delete(id);
            }
            resp.sendRedirect(req.getContextPath()+"/admin/categories");
        }
    }


