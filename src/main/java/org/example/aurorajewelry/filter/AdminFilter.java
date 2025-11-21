package org.example.aurorajewelry.filter;

import org.example.aurorajewelry.model.Employee;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Integer role = (Integer) session.getAttribute("roleId");

        if (role == null || role != 1) { // 1 = Admin
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}

