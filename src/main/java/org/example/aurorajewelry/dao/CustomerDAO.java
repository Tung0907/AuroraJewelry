package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Customer;
import org.example.aurorajewelry.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean create(Customer cst) {
        String sql = "INSERT INTO Customers(FullName, Phone, Email, Address, Password) VALUES(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, cst.getFullName());
            ps.setString(2, cst.getPhone());
            ps.setString(3, cst.getEmail());
            ps.setString(4, cst.getAddress());
            ps.setString(5, cst.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT CustomerID, FullName, Phone, Email, Address, CreatedAt FROM Customers ORDER BY CustomerID";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer cu = new Customer();
                cu.setCustomerId(rs.getInt("CustomerID"));
                cu.setFullName(rs.getString("FullName"));
                cu.setPhone(rs.getString("Phone"));
                cu.setEmail(rs.getString("Email"));
                cu.setAddress(rs.getString("Address"));
                cu.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(cu);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean update(Customer cst) {
        String sql = "UPDATE Customers SET FullName=?, Phone=?, Email=?, Address=? WHERE CustomerID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cst.getFullName());
            ps.setString(2, cst.getPhone());
            ps.setString(3, cst.getEmail());
            ps.setString(4, cst.getAddress());
            ps.setInt(5, cst.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Customers WHERE CustomerID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Customer findByEmailAndPassword(String email, String password) {
        String sql = "SELECT CustomerID, FullName, Phone, Email, Address, Password, CreatedAt FROM Customers WHERE Email=? AND Password=?";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer cu = new Customer();
                    cu.setCustomerId(rs.getInt("CustomerID"));
                    cu.setFullName(rs.getString("FullName"));
                    cu.setPhone(rs.getString("Phone"));
                    cu.setEmail(rs.getString("Email"));
                    cu.setAddress(rs.getString("Address"));
                    cu.setPassword(rs.getString("Password"));
                    cu.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    return cu;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }


}
