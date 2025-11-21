package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Employee;
import org.example.aurorajewelry.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT EmployeeID, FullName, Email, Role, CreatedAt FROM Employees ORDER BY EmployeeID";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeId(rs.getInt("EmployeeID"));
                e.setFullName(rs.getString("FullName"));
                e.setEmail(rs.getString("Email"));
                e.setRole(rs.getString("Role"));
                e.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(e);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public boolean create(Employee e) {
        String sql = "INSERT INTO Employees(FullName, Email, Password, Role) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getFullName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPassword());
            ps.setString(4, e.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    public boolean update(Employee e) {
        String sql = "UPDATE Employees SET FullName=?, Email=?, Password=?, Role=? WHERE EmployeeID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getFullName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPassword());
            ps.setString(4, e.getRole());
            ps.setInt(5, e.getEmployeeId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Employees WHERE EmployeeID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

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
    public Employee findByEmailAndPassword(String email, String password) {
        String sql = "SELECT EmployeeID, FullName, Email, Password, Role FROM Employees WHERE Email=? AND Password=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getInt("EmployeeID"));
                    e.setFullName(rs.getString("FullName"));
                    e.setEmail(rs.getString("Email"));
                    e.setRole(rs.getString("Role"));
                    return e;
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }
    public Employee login(String email, String password) {
        return findByEmailAndPassword(email, password);
    }

}
