package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;
import java.sql.*;
import java.util.*;

public class CategoryDAO {

    public List<Map<String,Object>> findAll() {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName, Description FROM Categories ORDER BY CategoryID";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String,Object> m = new HashMap<>();
                m.put("id", rs.getInt("CategoryID"));
                m.put("name", rs.getString("CategoryName"));
                m.put("desc", rs.getString("Description"));
                list.add(m);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(String name, String desc) {
        String sql = "INSERT INTO Categories(CategoryName, Description) VALUES(?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, desc);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(int id, String name, String desc) {
        String sql = "UPDATE Categories SET CategoryName=?, Description=? WHERE CategoryID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Categories WHERE CategoryID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Map<String,Object> findById(int id) {
        String sql = "SELECT CategoryID, CategoryName, Description FROM Categories WHERE CategoryID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("id", rs.getInt("CategoryID"));
                    m.put("name", rs.getString("CategoryName"));
                    m.put("desc", rs.getString("Description"));
                    return m;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
