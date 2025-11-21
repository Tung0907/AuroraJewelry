package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Discount;
import org.example.aurorajewelry.util.DBUtil;

import java.sql.*;
import java.util.*;

public class DiscountDAO {
    public List<Discount> findActive() {
        List<Discount> list = new ArrayList<>();
        String sql = "SELECT DiscountID, DiscountName, DiscountPercent, StartDate, EndDate FROM Discounts WHERE GETDATE() BETWEEN StartDate AND EndDate";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Discount d = new Discount();
                d.setDiscountId(rs.getInt("DiscountID"));
                d.setDiscountName(rs.getString("DiscountName"));
                d.setDiscountPercent(rs.getInt("DiscountPercent"));
                d.setStartDate(rs.getDate("StartDate"));
                d.setEndDate(rs.getDate("EndDate"));
                list.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
