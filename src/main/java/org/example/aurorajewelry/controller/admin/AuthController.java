package org.example.aurorajewelry.controller.admin;

import org.example.aurorajewelry.dao.EmployeeDAO;
import org.example.aurorajewelry.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({"/login", "/do-login", "/logout", "/register", "/do-register"})
public class AuthController extends HttpServlet {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.endsWith("/register")) {
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }
        // default login
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
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
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("employee", user);
            session.setAttribute("role", user.getRole());

            if ("admin".equalsIgnoreCase(user.getRole()) || "Admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                // staff -> pos
                resp.sendRedirect(req.getContextPath() + "/pos");
            }
            return;
        }

        if (uri.endsWith("/do-register")) {
            // simple create employee (for admin use) — you can add validation & password hashing
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role"); // "Staff" or "Admin"

            Employee e = new Employee();
            e.setFullName(fullName);
            e.setEmail(email);
            e.setPassword(password);
            e.setRole(role == null ? "Staff" : role);

            boolean ok = employeeDAO.create(e);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("msg", "Đăng ký thất bại");
                req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            }
            return;
        }
    }
}
