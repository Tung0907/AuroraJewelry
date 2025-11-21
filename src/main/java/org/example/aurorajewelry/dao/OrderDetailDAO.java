package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

public class OrderDetailDAO {

    public boolean addDetail(int orderId, int productId, Integer variantId, int qty, BigDecimal price) {
        String sql = "INSERT INTO OrderDetails(OrderID, ProductID, VariantID, Quantity, Price) VALUES(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            if (variantId == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, variantId);
            ps.setInt(4, qty);
            ps.setBigDecimal(5, price);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
