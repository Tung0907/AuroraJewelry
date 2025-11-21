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
        "/login",
        "/do-login",
        "/logout"
})
public class AuthController extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // Đăng xuất
        if (uri.endsWith("/logout")) {
            req.getSession().invalidate(); // Hủy session khi logout
            resp.sendRedirect(req.getContextPath() + "/login"); // Chuyển hướng về trang đăng nhập
            return;
        }

        // Hiển thị trang đăng nhập chung
        if (uri.endsWith("/login")) {
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/do-login")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Kiểm tra đăng nhập admin hoặc thu ngân
            Employee emp = employeeDAO.findByEmailAndPassword(email, password);
            if (emp != null) {
                // Lưu thông tin người dùng vào session
                HttpSession session = req.getSession();
                session.setAttribute("user", emp);
                session.setAttribute("role", emp.getRole());

                // Chuyển hướng sau khi đăng nhập thành công
                if ("Admin".equalsIgnoreCase(emp.getRole()) || "Staff".equalsIgnoreCase(emp.getRole())) {
                    resp.sendRedirect(req.getContextPath() + "/pos"); // Nếu là admin hoặc staff sẽ vào POS
                    return;
                }
            }

            // Kiểm tra đăng nhập khách hàng
            Customer customer = customerDAO.findByEmailAndPassword(email, password);
            if (customer != null) {
                // Lưu thông tin khách hàng vào session
                HttpSession session = req.getSession();
                session.setAttribute("user", customer);

                // Chuyển hướng đến trang mua hàng online
                resp.sendRedirect(req.getContextPath() + "/customer/home");
                return;
            }

            // Nếu không tìm thấy tài khoản hợp lệ
            req.setAttribute("msg", "Email hoặc mật khẩu không đúng");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp); // Quay lại trang đăng nhập
        }
    }
}
