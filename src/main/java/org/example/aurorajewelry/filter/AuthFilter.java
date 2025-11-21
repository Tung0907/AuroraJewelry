package org.example.aurorajewelry.filter;

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
        // Public resources (no auth)
        if (path.startsWith("/assets") || path.startsWith("/uploads") || path.startsWith("/css")
                || path.startsWith("/js") || path.startsWith("/images") || path.equals("/login")
                || path.equals("/do-login") || path.equals("/register") || path.equals("/do-register")
                || path.equals("/") ) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Object emp = (session == null) ? null : session.getAttribute("employee");

        // If user not logged in -> redirect to login
        if (emp == null) {
            // If request is an API (json) you may want to return 401, here simple redirect
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // user logged in -> role based access
        // example: only Admin can access /admin/*
        String role = (String) req.getSession().getAttribute("role");
        if (path.startsWith("/admin") && (role == null || !role.equalsIgnoreCase("Admin"))) {
            // staff may access POS only
            if (path.startsWith("/pos") && "Staff".equalsIgnoreCase(role)) {
                chain.doFilter(request, response);
                return;
            }
            // not authorized
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
