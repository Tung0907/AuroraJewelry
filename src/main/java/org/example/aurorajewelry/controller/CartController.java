package org.example.aurorajewelry.controller;

import org.example.aurorajewelry.dao.ProductDAO;
import org.example.aurorajewelry.dao.ProductImageDAO;
import org.example.aurorajewelry.dao.ProductVariantDAO;
import org.example.aurorajewelry.model.CartItem;
import org.example.aurorajewelry.model.ProductVariant;
import org.example.aurorajewelry.model.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@WebServlet(name="cartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private ProductImageDAO imageDAO = new ProductImageDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<String, CartItem> cart = getCart(session);

        if ("add".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            String variantIdStr = req.getParameter("variantId"); // may be null
            Integer variantId = (variantIdStr == null || variantIdStr.isEmpty()) ? null : Integer.valueOf(variantIdStr);
            int qty = Integer.parseInt(req.getParameter("qty"));

            Product p = productDAO.findById(productId);
            if (p == null) { resp.sendRedirect(req.getContextPath()+"/product"); return; }

            BigDecimal basePrice = p.getPrice();
            String image = (imageDAO.findByProductId(productId).isEmpty()) ? "assets/images/default.png" : imageDAO.findByProductId(productId).get(0);
            String variantSize = null;
            BigDecimal add = BigDecimal.ZERO;
            if (variantId != null) {
                ProductVariant v = variantDAO.findById(variantId);
                if (v != null) {
                    variantSize = v.getSize();
                    if (v.getAdditionalPrice() != null) add = v.getAdditionalPrice();
                }
            }
            BigDecimal unitPrice = basePrice.add(add);

            // key = productId_variantId
            String key = productId + "_" + (variantId == null ? 0 : variantId);

            CartItem item = cart.get(key);
            if (item == null) {
                item = new CartItem();
                item.setProductId(productId);
                item.setVariantId(variantId);
                item.setProductName(p.getProductName());
                item.setUnitPrice(unitPrice);
                item.setQuantity(qty);
                item.setImageUrl(image);
                item.setVariantSize(variantSize);
                cart.put(key, item);
            } else {
                item.setQuantity(item.getQuantity() + qty);
            }

            session.setAttribute("cart", cart);
            resp.sendRedirect(req.getContextPath() + "/cart?action=view");

        } else if ("update".equals(action)) {
            // params: key, qty
            String key = req.getParameter("key");
            int qty = Integer.parseInt(req.getParameter("qty"));
            CartItem item = cart.get(key);
            if (item != null) {
                if (qty <= 0) cart.remove(key); else item.setQuantity(qty);
            }
            resp.sendRedirect(req.getContextPath() + "/cart?action=view");
        } else if ("remove".equals(action)) {
            String key = req.getParameter("key");
            cart.remove(key);
            resp.sendRedirect(req.getContextPath() + "/cart?action=view");
        } else {
            resp.sendRedirect(req.getContextPath()+"/cart?action=view");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isEmpty() || action.equals("view")) {
            req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath()+"/cart?action=view");
        }
    }
}
