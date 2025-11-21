package org.example.aurorajewelry.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.example.aurorajewelry.dao.StatisticsDAO;

@WebServlet("/admin/dashboard")
public class AdminDashboardController extends HttpServlet {
    private StatisticsDAO stats = new StatisticsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productCount = stats.countProducts();
        int orderCount = stats.countOrders();
        req.setAttribute("productCount", productCount);
        req.setAttribute("orderCount", orderCount);
        req.setAttribute("revenueByMonth", stats.revenueByMonth(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)));
        req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
    }
}
