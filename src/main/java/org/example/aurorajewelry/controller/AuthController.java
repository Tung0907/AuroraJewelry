package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.EmployeeDAO;
import org.example.aurorajewelry.dao.CustomerDAO;
import org.example.aurorajewelry.model.Employee;
import org.example.aurorajewelry.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({
        "/admin/login",
        "/admin/do-login",
        "/admin/logout",
        "/admin/register",
        "/admin/do-register"
})
public class AuthController extends HttpServlet {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/logout")) {
            req.getSession().invalidate(); // Hủy session khi logout
            resp.sendRedirect(req.getContextPath() + "/admin/login"); // Chuyển hướng về trang đăng nhập
            return;
        }

        if (uri.endsWith("/register")) {
            req.getRequestDispatcher("/views/auth/admin_register.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher("/views/auth/admin_login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/do-login")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            Employee user = employeeDAO.findByEmailAndPassword(email, password);

            if (user == null) {
                req.setAttribute("msg", "Email hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/auth/admin_login.jsp").forward(req, resp);
                return;
            }

            req.getSession().setAttribute("employee", user);

            if (user.getRole().equalsIgnoreCase("Admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard"); // Admin vào trang quản lý
            } else {
                resp.sendRedirect(req.getContextPath() + "/pos"); // Thu ngân vào bán hàng tại quầy
            }
            return;
        }

        if (uri.endsWith("/do-register")) {
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role");

            Employee emp = new Employee();
            emp.setFullName(fullName);
            emp.setEmail(email);
            emp.setPassword(password);
            emp.setRole(role);

            boolean ok = employeeDAO.create(emp);

            if (ok) resp.sendRedirect(req.getContextPath() + "/admin/login");
            else {
                req.setAttribute("msg", "Đăng ký thất bại");
                req.getRequestDispatcher("/views/auth/admin_register.jsp").forward(req, resp);
            }
        }
    }
}
