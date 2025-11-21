package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {
    public List<String> findByProductId(int productId) {
        List<String> imgs = new ArrayList<>();
        String sql = "SELECT ImageURL FROM ProductImages WHERE ProductID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) imgs.add(rs.getString("ImageURL"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return imgs;
    }
}
