package org.example.aurorajewelry.filter;

import org.example.aurorajewelry.model.Customer;
import org.example.aurorajewelry.model.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Nếu chưa đăng nhập và đang cố gắng vào trang cần bảo vệ
        HttpSession session = req.getSession(false);
        Object user = (session == null) ? null : session.getAttribute("user");

        // Trường hợp người dùng chưa đăng nhập và đang vào trang bảo mật (admin, pos, v.v.)
        if (user == null) {
            if (path.startsWith("/admin/")) {
                resp.sendRedirect(req.getContextPath() + "/admin/login");  // Chuyển hướng đến trang đăng nhập admin
            } else if (path.startsWith("/pos")) {
                resp.sendRedirect(req.getContextPath() + "/customer/login");  // Chuyển hướng đến trang đăng nhập customer
            } else {
                chain.doFilter(request, response);  // Trang công khai như /index.jsp không cần phải đăng nhập
            }
            return;
        }

        // Phân quyền cho Employee (Admin, Staff)
        if (user instanceof Employee) {
            Employee emp = (Employee) user;
            String role = emp.getRole();

            // Admin và Staff chỉ có thể truy cập trang admin và POS
            if (path.startsWith("/admin/") && !"Admin".equalsIgnoreCase(role) && !"Staff".equalsIgnoreCase(role)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập");
                return;
            }
            if (path.startsWith("/pos") && !("Admin".equalsIgnoreCase(role) || "Staff".equalsIgnoreCase(role))) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập POS");
                return;
            }
        }

        // Phân quyền cho Customer
        else if (user instanceof Customer) {
            if (path.startsWith("/admin/") || path.startsWith("/pos")) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập");
                return;
            }
        }

        chain.doFilter(request, response);  // Tiếp tục nếu không có lỗi
    }

    @Override
    public void destroy() {}
}
