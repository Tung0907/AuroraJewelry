package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.OrderDAO;
import org.example.aurorajewelry.dao.OrderDetailDAO;
import org.example.aurorajewelry.model.CartItem;
import org.example.aurorajewelry.model.Order;
import org.example.aurorajewelry.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO detailDAO = new OrderDetailDAO();

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/checkout/checkout.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, CartItem> cart = (Map<String, CartItem>) req.getSession().getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            req.setAttribute("msg", "Giỏ hàng trống");
            req.getRequestDispatcher("/views/checkout/checkout.jsp").forward(req, resp);
            return;
        }

        // For demo: create order with customer null (guest) or if logged in customer session use id
        Integer customerId = null;
        Object cusObj = req.getSession().getAttribute("customer");
        if (cusObj instanceof Customer) customerId = ((Customer) cusObj).getCustomerId();

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : cart.values()) total = total.add(it.getUnitPrice().multiply(new BigDecimal(it.getQuantity())));

        int employeeId = 1; // system default for online orders, or set to 0
        int orderId = orderDAO.createOrder(customerId, employeeId, "Online", total);
        if (orderId <= 0) {
            req.setAttribute("msg", "Tạo đơn hàng thất bại");
            req.getRequestDispatcher("/views/checkout/checkout.jsp").forward(req, resp);
            return;
        }

        boolean ok = true;
        for (CartItem it : cart.values()) {
            boolean added = detailDAO.addDetail(orderId, it.getProductId(), it.getVariantId(), it.getQuantity(), it.getUnitPrice());
            if (!added) ok = false;
        }

        if (ok) {
            orderDAO.updatePayment(orderId, "OnlineCard", "Paid"); // demo: set paid
            req.getSession().removeAttribute("cart");
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/views/checkout/success.jsp").forward(req, resp);
        } else {
            req.setAttribute("msg", "Lỗi lưu chi tiết đơn");
            req.getRequestDispatcher("/views/checkout/checkout.jsp").forward(req, resp);
        }
    }
}
