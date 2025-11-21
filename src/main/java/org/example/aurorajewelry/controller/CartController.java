package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.model.CartItem;
import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet({"/cart", "/cart/add", "/cart/remove"})
public class CartController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @SuppressWarnings("unchecked")
    private Map<String, CartItem> getCart(HttpSession session) {
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show cart
        req.getRequestDispatcher("/views/cart/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/cart/add")) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int qty = Integer.parseInt(req.getParameter("qty"));
            Product p = productDAO.findById(productId);
            if (p == null) { resp.sendRedirect(req.getContextPath()+"/"); return; }

            String key = String.valueOf(productId); // no variant in this simple flow
            Map<String, CartItem> cart = getCart(req.getSession());
            CartItem it = cart.get(key);
            if (it == null) {
                it = new CartItem();
                it.setProductId(productId);
                it.setProductName(p.getProductName());
                it.setQuantity(qty);
                it.setUnitPrice(p.getPrice());
                it.setImageUrl(p.getFirstImage());
                cart.put(key, it);
            } else {
                it.setQuantity(it.getQuantity() + qty);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if (uri.endsWith("/cart/remove")) {
            String key = req.getParameter("key");
            Map<String, CartItem> cart = getCart(req.getSession());
            cart.remove(key);
            resp.sendRedirect(req.getContextPath()+"/cart");
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/");
    }
}
