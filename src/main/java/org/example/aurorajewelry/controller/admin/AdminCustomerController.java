package org.example.aurorajewelry.controller.admin;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import org.example.aurorajewelry.dao.CustomerDAO;
import org.example.aurorajewelry.model.Customer;

import java.io.IOException;

@WebServlet(name="adminCustomers", urlPatterns={"/admin/customers"})
public class AdminCustomerController extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("customers", customerDAO.findAll());
        req.getRequestDispatcher("/views/admin/customers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("update".equals(action)) {
            Customer c = new Customer();
            c.setCustomerId(Integer.parseInt(req.getParameter("id")));
            c.setFullName(req.getParameter("fullname"));
            c.setPhone(req.getParameter("phone"));
            c.setEmail(req.getParameter("email"));
            c.setAddress(req.getParameter("address"));
            customerDAO.update(c);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            customerDAO.delete(id);
        }
        resp.sendRedirect(req.getContextPath()+"/admin/customers");
    }
}

