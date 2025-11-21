package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Customer;
import org.example.aurorajewelry.util.DBUtil;

import java.sql.*;

public class CustomerDAO {

    public Customer findById(int id) {
        String sql = "SELECT CustomerID, FullName, Phone, Email, Address, CreatedAt FROM Customers WHERE CustomerID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer cu = new Customer();
                    cu.setCustomerId(rs.getInt("CustomerID"));
                    cu.setFullName(rs.getString("FullName"));
                    cu.setPhone(rs.getString("Phone"));
                    cu.setEmail(rs.getString("Email"));
                    cu.setAddress(rs.getString("Address"));
                    cu.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    return cu;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public int create(Customer cst) {
        String sql = "INSERT INTO Customers(FullName, Phone, Email, Address) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cst.getFullName());
            ps.setString(2, cst.getPhone());
            ps.setString(3, cst.getEmail());
            ps.setString(4, cst.getAddress());
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }
}
