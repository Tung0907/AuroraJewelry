package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.CustomerDAO;
import org.example.aurorajewelry.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({"/register", "/do-register"})
public class CustomerController extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show register form
        req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // perform register (do-register)
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        Customer c = new Customer();
        c.setFullName(fullName);
        c.setEmail(email);
        c.setPhone(phone);
        c.setPassword(password); // TODO: hash in prod

        boolean ok = customerDAO.create(c);
        if (!ok) {
            req.setAttribute("msg", "Đăng ký thất bại (email có thể đã tồn tại)");
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }

        // After register, auto-login customer
        Customer saved = customerDAO.findByEmailAndPassword(email, password);
        req.getSession().setAttribute("customer", saved);
        resp.sendRedirect(req.getContextPath() + "/"); // home / catalog
    }
}
