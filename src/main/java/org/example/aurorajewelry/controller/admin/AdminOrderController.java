package org.example.aurorajewelry.controller.admin;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import org.example.aurorajewelry.dao.OrderDAO;
import org.example.aurorajewelry.dao.OrderDetailDAO;
import org.example.aurorajewelry.dao.ProductVariantDAO;
import org.example.aurorajewelry.model.OrderDetail;

@WebServlet("/admin/orders")
public class AdminOrderController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO detailDAO = new OrderDetailDAO();
    private final ProductVariantDAO variantDAO = new ProductVariantDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("orders", orderDAO.findAll());
        req.getRequestDispatcher("/views/admin/orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        switch (action) {

            case "updateStatus" -> {
                String status = req.getParameter("status");
                orderDAO.updateStatus(orderId, status);
            }

            case "cancel" -> {
                boolean ok = orderDAO.cancelOrder(orderId);
                if (ok) {
                    List<OrderDetail> details = detailDAO.findByOrderId(orderId);
                    for (OrderDetail od : details) {
                        if (od.getVariantId() != null)
                            variantDAO.increaseStock(od.getVariantId(), od.getQuantity());
                    }
                }
            }

            case "return" -> {
                String reason = req.getParameter("reason");
                boolean ok = orderDAO.returnOrder(orderId, reason);

                if (ok) {
                    List<OrderDetail> details = detailDAO.findByOrderId(orderId);
                    for (OrderDetail od : details) {
                        if (od.getVariantId() != null)
                            variantDAO.increaseStock(od.getVariantId(), od.getQuantity());
                    }
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}
