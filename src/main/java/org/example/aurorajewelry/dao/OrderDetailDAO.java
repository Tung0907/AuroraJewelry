package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.OrderDetail;
import org.example.aurorajewelry.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<OrderDetail> findByOrderId(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT OrderDetailID, OrderID, ProductID, VariantID, Quantity, Price FROM OrderDetails WHERE OrderID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail od = new OrderDetail();
                    od.setOrderDetailId(rs.getInt("OrderDetailID"));
                    od.setOrderId(rs.getInt("OrderID"));
                    od.setProductId(rs.getInt("ProductID"));
                    od.setVariantId((Integer) (rs.getObject("VariantID")));
                    od.setQuantity(rs.getInt("Quantity"));
                    od.setPrice(rs.getBigDecimal("Price"));
                    list.add(od);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public boolean insert(int orderId, int productId, Integer variantId, int qty, int priceInt) {
        return addDetail(orderId, productId, variantId, qty, new BigDecimal(priceInt));
    }
}
