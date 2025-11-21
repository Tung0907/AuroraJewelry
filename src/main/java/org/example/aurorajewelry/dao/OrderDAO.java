package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

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
}
