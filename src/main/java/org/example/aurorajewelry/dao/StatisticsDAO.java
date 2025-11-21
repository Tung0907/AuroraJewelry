package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Order;
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
    public int countProducts() {
        String sql = "SELECT COUNT(*) FROM Products";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countCategories() {
        String sql = "SELECT COUNT(*) FROM Categories";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countOrders() {
        String sql = "SELECT COUNT(*) FROM Orders";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int countCustomers() {
        String sql = "SELECT COUNT(*) FROM Customers";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();

        String sql = """
        SELECT OrderID, CustomerID, EmployeeID, OrderDate,
               TotalAmount, PaymentMethod, Status
        FROM Orders
    """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("OrderID"));
                o.setCustomerId(rs.getInt("CustomerID"));
                o.setEmployeeId(rs.getInt("EmployeeID"));
                o.setCreatedAt(rs.getTimestamp("OrderDate"));
                o.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                o.setPaymentMethod(rs.getString("PaymentMethod"));
                o.setStatus(rs.getString("Status"));
                list.add(o);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

}
