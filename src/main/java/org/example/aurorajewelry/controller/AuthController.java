package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.EmployeeDAO;
import org.example.aurorajewelry.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({
        "/admin/login",
        "/admin/do-login",
        "/admin/logout"
})
public class AuthController extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // Đăng xuất
        if (uri.endsWith("/admin/logout")) {
            req.getSession().invalidate(); // Hủy session khi logout
            resp.sendRedirect(req.getContextPath() + "/admin/login"); // Chuyển hướng về trang đăng nhập
            return;
        }

        // Hiển thị trang đăng nhập
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/admin/do-login")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Kiểm tra đăng nhập
            Employee emp = employeeDAO.findByEmailAndPassword(email, password);
            if (emp == null) {
                req.setAttribute("msg", "Email hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                return;
            }

            // Lưu thông tin người dùng vào session
            HttpSession session = req.getSession();
            session.setAttribute("user", emp);
            session.setAttribute("role", emp.getRole());

            // Chuyển hướng sau khi đăng nhập thành công
            if ("Admin".equalsIgnoreCase(emp.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/pos");
            }
        }
    }
}
