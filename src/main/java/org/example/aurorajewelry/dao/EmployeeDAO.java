package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Employee;
import org.example.aurorajewelry.util.DBUtil;

import java.sql.*;

public class EmployeeDAO {
    public Employee findById(int id) {
        String sql = "SELECT EmployeeID, FullName, Email, Password, Role, CreatedAt FROM Employees WHERE EmployeeID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getInt("EmployeeID"));
                    e.setFullName(rs.getString("FullName"));
                    e.setEmail(rs.getString("Email"));
                    e.setPassword(rs.getString("Password"));
                    e.setRole(rs.getString("Role"));
                    e.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    return e;
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }
}
