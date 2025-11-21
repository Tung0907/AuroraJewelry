package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {

    public boolean insert(int productId, String imageUrl) {
        String sql = "INSERT INTO ProductImages(ProductID, ImageURL) VALUES(?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, imageUrl);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<String> findByProductId(int productId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ImageURL FROM ProductImages WHERE ProductID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getString("ImageURL"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean delete(String imageUrl) {
        String sql = "DELETE FROM ProductImages WHERE ImageURL=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, imageUrl);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
