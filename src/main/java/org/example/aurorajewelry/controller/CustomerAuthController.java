package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.CustomerDAO;
import org.example.aurorajewelry.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({
        "/customer/login",
        "/customer/do-login",
        "/customer/logout",
        "/customer/register",
        "/customer/do-register"
})
public class CustomerAuthController extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/customer/logout")) {
            req.getSession().invalidate(); // Đăng xuất khách hàng
            resp.sendRedirect(req.getContextPath() + "/"); // Quay lại trang chủ
            return;
        }
        if (uri.endsWith("/customer/login")) {
            req.getRequestDispatcher("/views/auth/customer_login.jsp").forward(req, resp);
            return;
        }
        if (uri.endsWith("/customer/register")) {
            req.getRequestDispatcher("/views/auth/customer_register.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/"); // Nếu không khớp với bất kỳ url nào, quay lại trang chủ
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/customer/do-login")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Kiểm tra đăng nhập khách hàng
            Customer cus = customerDAO.findByEmailAndPassword(email, password);

            if (cus == null) {
                req.setAttribute("msg", "Email hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/auth/customer_login.jsp").forward(req, resp);
                return;
            }

            req.getSession().setAttribute("customer", cus);
            resp.sendRedirect(req.getContextPath() + "/"); // Chuyển hướng đến trang chủ sau khi đăng nhập thành công
            return;
        }

        if (uri.endsWith("/customer/do-register")) {
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");

            Customer c = new Customer();
            c.setFullName(fullName);
            c.setEmail(email);
            c.setPhone(phone);
            c.setPassword(password);

            boolean ok = customerDAO.create(c);
            if (!ok) {
                req.setAttribute("msg", "Đăng ký thất bại (email có thể đã tồn tại)");
                req.getRequestDispatcher("/views/auth/customer_register.jsp").forward(req, resp);
                return;
            }

            Customer saved = customerDAO.findByEmailAndPassword(email, password);
            req.getSession().setAttribute("customer", saved);
            resp.sendRedirect(req.getContextPath() + "/"); // Chuyển hướng đến trang chủ sau khi đăng ký thành công
        }
    }
}
