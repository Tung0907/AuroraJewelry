package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.util.DBUtil;
import java.sql.*;
import java.util.*;

public class CategoryDAO {

    public List<Map<String,Object>> findAll() {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName, Description FROM Categories";
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
}
