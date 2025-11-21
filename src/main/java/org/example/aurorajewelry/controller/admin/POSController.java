package org.example.aurorajewelry.controller.admin;

import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.dao.ProductVariantDAO;
import org.example.aurorajewelry.dao.OrderDAO;
import org.example.aurorajewelry.dao.OrderDetailDAO;
import org.example.aurorajewelry.model.CartItem;
import org.example.aurorajewelry.model.Product;
import org.example.aurorajewelry.model.ProductVariant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@WebServlet("/pos")
public class POSController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    @SuppressWarnings("unchecked")
    private Map<String, CartItem> getPosCart(HttpSession session) {
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("posCart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("posCart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // load quick data
        req.setAttribute("products", productDAO.findAll()); // small list
        req.getRequestDispatcher("/views/admin/pos.jsp").forward(req, resp);
    }

    // actions: add, update, remove, checkout
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<String, CartItem> cart = getPosCart(session);

        if ("add".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            String variantIdStr = req.getParameter("variantId");
            Integer variantId = (variantIdStr == null || variantIdStr.isEmpty()) ? null : Integer.valueOf(variantIdStr);
            int qty = Integer.parseInt(req.getParameter("qty"));

            Product p = productDAO.findById(productId);
            if (p == null) { resp.sendRedirect(req.getContextPath()+"/pos"); return; }

            BigDecimal basePrice = p.getPrice();
            BigDecimal add = BigDecimal.ZERO;
            String variantSize = null;
            if (variantId != null) {
                ProductVariant v = variantDAO.findById(variantId);
                if (v != null) {
                    variantSize = v.getSize();
                    if (v.getAdditionalPrice() != null) add = v.getAdditionalPrice();
                }
            }

            BigDecimal unitPrice = basePrice.add(add);
            String key = productId + "_" + (variantId == null ? 0 : variantId);

            CartItem item = cart.get(key);
            if (item == null) {
                item = new CartItem();
                item.setProductId(productId);
                item.setVariantId(variantId);
                item.setProductName(p.getProductName());
                item.setUnitPrice(unitPrice);
                item.setQuantity(qty);
                cart.put(key, item);
            } else {
                item.setQuantity(item.getQuantity() + qty);
            }

            resp.sendRedirect(req.getContextPath() + "/pos");
            return;
        }

        if ("remove".equals(action)) {
            String key = req.getParameter("key");
            cart.remove(key);
            resp.sendRedirect(req.getContextPath()+"/pos");
            return;
        }

        if ("checkout".equals(action)) {
            // simple checkout flow for POS
            String paymentMethod = req.getParameter("paymentMethod"); // Cash / CardOnSwipe
            BigDecimal total = BigDecimal.ZERO;
            for (CartItem it : cart.values()) total = total.add(it.getUnitPrice().multiply(new BigDecimal(it.getQuantity())));

            int employeeId = 1; // giả sử
            Integer customerId = null; // POS thường không bắt buộc

            int orderId = orderDAO.createOrder(customerId, employeeId, "POS", total);
            if (orderId <= 0) {
                req.setAttribute("msg", "Tạo đơn hàng thất bại");
                req.getRequestDispatcher("/views/admin/pos.jsp").forward(req, resp);
                return;
            }

            boolean ok = true;
            for (CartItem it : cart.values()) {
                boolean added = orderDetailDAO.addDetail(orderId, it.getProductId(), it.getVariantId(), it.getQuantity(), it.getUnitPrice());
                if (!added) ok = false;
                if (it.getVariantId() != null) variantDAO.decreaseStock(it.getVariantId(), it.getQuantity());
            }

            if (ok) {
                // update order payment info
                orderDAO.updatePayment(orderId, paymentMethod, "Paid");
                session.removeAttribute("posCart");
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/views/admin/pos_success.jsp").forward(req, resp);
            } else {
                req.setAttribute("msg", "Lỗi lưu chi tiết đơn");
                req.getRequestDispatcher("/views/admin/pos.jsp").forward(req, resp);
            }
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/pos");
    }
}
