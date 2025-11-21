package org.example.aurorajewelry.controller.admin;

import org.example.aurorajewelry.dao.OrderDAO;
import org.example.aurorajewelry.dao.OrderDetailDAO;
import org.example.aurorajewelry.model.Employee;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/pos/checkout")
public class POSCheckoutServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO detailDAO = new OrderDetailDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String cartJson = req.getParameter("cartData");
        int employeeId = ((Employee) req.getSession().getAttribute("employee")).getEmployeeId();

        // Parse JSON
        JSONArray arr = new JSONArray(cartJson);

        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            total = total.add(
                    BigDecimal.valueOf(o.getInt("price"))
                            .multiply(BigDecimal.valueOf(o.getInt("qty")))
            );
        }

        // 1. create order
        int orderId = orderDAO.createOrder(null, employeeId, "POS", total);

        // 2. insert order details
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            detailDAO.addDetail(
                    orderId,
                    o.getInt("id"),
                    null,
                    o.getInt("qty"),
                    BigDecimal.valueOf(o.getInt("price"))
            );
        }

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}
