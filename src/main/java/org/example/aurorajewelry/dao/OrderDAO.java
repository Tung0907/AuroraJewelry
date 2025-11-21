package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Order;
import org.example.aurorajewelry.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // create order and return generated OrderID
    public int createOrder(Integer customerId, int employeeId, String orderType, BigDecimal total) {
        String sql = "INSERT INTO Orders(CustomerID, EmployeeID, OrderType, TotalAmount) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (customerId == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, customerId);
            ps.setInt(2, employeeId);
            ps.setString(3, orderType);
            ps.setBigDecimal(4, total);
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }
    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET Status=? WHERE OrderID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // cancel with condition (only if not shipped)
    public boolean cancelOrder(int orderId) {
        String checkSql = "SELECT Status FROM Orders WHERE OrderID=?";
        String updateSql = "UPDATE Orders SET Status='Cancelled' WHERE OrderID=? AND Status IN ('Pending','Confirmed')";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps1 = c.prepareStatement(checkSql)) {

            ps1.setInt(1, orderId);
            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    String cur = rs.getString("Status");
                    if (cur.equals("Shipping") || cur.equals("Completed") || cur.equals("Cancelled") || cur.equals("Returned")) {
                        return false;
                    }
                }
            }

            try (PreparedStatement ps2 = c.prepareStatement(updateSql)) {
                ps2.setInt(1, orderId);
                return ps2.executeUpdate() > 0;
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // mark returned (only if Completed)
    public boolean returnOrder(int orderId, String reason) {
        String updateSql = "UPDATE Orders SET Status='Returned', ReturnedReason=? WHERE OrderID=? AND Status='Completed'";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(updateSql)) {
            ps.setString(1, reason);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // trong OrderDAO.java
    public boolean updatePayment(int orderId, String method, String status) {
        String sql = "UPDATE Orders SET PaymentMethod=?, PaymentStatus=? WHERE OrderID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, method);
            ps.setString(2, status);
            ps.setInt(3, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();

        String sql = """
        SELECT OrderID, CustomerID, EmployeeID, OrderType,
               TotalAmount, CreatedAt
        FROM Orders
        ORDER BY OrderID DESC
    """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("OrderID"));
                o.setCustomerId((Integer) rs.getObject("CustomerID"));
                o.setEmployeeId(rs.getInt("EmployeeID"));
                o.setOrderType(rs.getString("OrderType"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
