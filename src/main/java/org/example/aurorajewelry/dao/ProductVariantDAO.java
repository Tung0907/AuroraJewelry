package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.ProductVariant;
import org.example.aurorajewelry.util.DBUtil;

import java.sql.*;
import java.util.*;

public class ProductVariantDAO {

    public List<ProductVariant> findByProductId(int productId) {
        List<ProductVariant> list = new ArrayList<>();
        String sql = "SELECT VariantID, ProductID, Size, Stock, AdditionalPrice FROM ProductVariants WHERE ProductID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductVariant v = new ProductVariant();
                    v.setVariantId(rs.getInt("VariantID"));
                    v.setProductId(rs.getInt("ProductID"));
                    v.setSize(rs.getString("Size"));
                    v.setStock(rs.getInt("Stock"));
                    v.setAdditionalPrice(rs.getBigDecimal("AdditionalPrice"));
                    list.add(v);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public ProductVariant findById(int id) {
        String sql = "SELECT VariantID, ProductID, Size, Stock, AdditionalPrice FROM ProductVariants WHERE VariantID=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductVariant v = new ProductVariant();
                    v.setVariantId(rs.getInt("VariantID"));
                    v.setProductId(rs.getInt("ProductID"));
                    v.setSize(rs.getString("Size"));
                    v.setStock(rs.getInt("Stock"));
                    v.setAdditionalPrice(rs.getBigDecimal("AdditionalPrice"));
                    return v;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean decreaseStock(int variantId, int qty) {
        String sql = "UPDATE ProductVariants SET Stock = Stock - ? WHERE VariantID=? AND Stock >= ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, variantId);
            ps.setInt(3, qty);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
