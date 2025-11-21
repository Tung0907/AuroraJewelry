package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;
import java.sql.*;
import java.util.*;

public class StatisticsDAO {
    public Map<String, Object> revenueByMonth(int year) {
        Map<String,Object> map = new LinkedHashMap<>();
        String sql = "SELECT MONTH(CreatedAt) m, SUM(TotalAmount) total FROM Orders WHERE YEAR(CreatedAt)=? GROUP BY MONTH(CreatedAt) ORDER BY MONTH(CreatedAt)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(String.valueOf(rs.getInt("m")), rs.getBigDecimal("total"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }

    public List<Map<String,Object>> topSellingProducts(int limit) {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, SUM(od.Quantity) sold FROM OrderDetails od JOIN Products p ON od.ProductID=p.ProductID GROUP BY p.ProductID, p.ProductName ORDER BY sold DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                int i=0;
                while (rs.next() && i<limit) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("productId", rs.getInt("ProductID"));
                    m.put("productName", rs.getString("ProductName"));
                    m.put("sold", rs.getInt("sold"));
                    list.add(m);
                    i++;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
