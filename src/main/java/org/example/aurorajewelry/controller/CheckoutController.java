package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.*;
import org.example.aurorajewelry.model.CartItem;
import org.example.aurorajewelry.model.Customer;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet(name="checkoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            req.setAttribute("msg", "Giỏ hàng rỗng");
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
            return;
        }

        // lấy thông tin khách (simple)
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        Customer customer = new Customer();
        customer.setFullName(fullname);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);

        int customerId = customerDAO.create(customer); // trả về id hoặc -1

        // tính tổng
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : cart.values()) total = total.add(it.getUnitPrice().multiply(new BigDecimal(it.getQuantity())));

        // EmployeeID required by schema -> dùng admin (1) tạm
        int employeeId = 1;

        int orderId = orderDAO.createOrder(customerId > 0 ? customerId : null, employeeId, "Online", total);
        if (orderId <= 0) {
            req.setAttribute("msg", "Tạo đơn hàng thất bại");
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
            return;
        }

        // lưu order details và giảm stock nếu variant được chọn
        boolean allOk = true;
        for (CartItem it : cart.values()) {
            Integer variantId = it.getVariantId();
            boolean added = orderDetailDAO.addDetail(orderId, it.getProductId(), variantId, it.getQuantity(), it.getUnitPrice());
            if (!added) allOk = false;

            if (variantId != null) {
                boolean dec = variantDAO.decreaseStock(variantId, it.getQuantity());
                if (!dec) {
                    // note: trong thực tế cần rollback, ở đây chỉ báo lỗi
                    allOk = false;
                }
            }
        }

        if (allOk) {
            session.removeAttribute("cart");
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("/views/checkout_success.jsp").forward(req, resp);
        } else {
            req.setAttribute("msg", "Có lỗi khi lưu chi tiết đơn hàng");
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }
}
