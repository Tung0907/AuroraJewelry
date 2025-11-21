package org.example.aurorajewelry.dao;

import org.example.aurorajewelry.model.Product;
import org.example.aurorajewelry.util.DBUtil;

import java.sql.*;
import java.util.*;

public class ProductDAO {

    // ============================
    //          CRUD
    // ============================
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT p.ProductID, p.CategoryID, p.ProductName, p.Material, p.Weight,
                   p.Gender, p.Description, p.Price,
                   c.CategoryName
            FROM Products p
            JOIN Categories c ON p.CategoryID = c.CategoryID
            """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(parseProduct(rs, c));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }


    public Product findById(int id) {
        String sql = """
    SELECT p.ProductID, p.CategoryID, p.ProductName, p.Material, p.Weight,
           p.Gender, p.Description, p.Price,
           c.CategoryName
    FROM Products p
    JOIN Categories c ON p.CategoryID = c.CategoryID
    WHERE p.ProductID = ?
    """;


        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return parseProduct(rs, c);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    public boolean insert(Product p) {
        String sql = """
                INSERT INTO Products(CategoryID, ProductName, Material, Weight, Gender, Description, Price)
                VALUES(?,?,?,?,?,?,?)
                """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            fillStatement(ps, p);

            if (ps.executeUpdate() > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int productId = rs.getInt(1);
                        insertImages(productId, p.getImages(), c);
                    }
                }

                return true;
            }

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean update(Product p) {
        String sql = """
                UPDATE Products
                SET CategoryID=?, ProductName=?, Material=?, Weight=?, Gender=?, Description=?, Price=?
                WHERE ProductID=?
                """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            fillStatement(ps, p);
            ps.setInt(8, p.getProductId());

            if (ps.executeUpdate() > 0) {

                deleteImages(p.getProductId(), c);
                insertImages(p.getProductId(), p.getImages(), c);

                return true;
            }

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean delete(int id) {
        try (Connection c = DBUtil.getConnection()) {

            // kiểm tra xem sản phẩm có nằm trong OrderDetails không
            if (existsInOrderDetails(id, c))
                return false; // không cho xóa

            deleteImages(id, c);

            String sql = "DELETE FROM Products WHERE ProductID=?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    // ============================
    //      TÌM KIẾM / LỌC
    // ============================

    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = """
                SELECT * FROM Products
                WHERE ProductName LIKE ?
                """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(parseProduct(rs, c));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public List<Product> filter(Integer categoryId, String material, String gender,
                                Double minPrice, Double maxPrice) {

        List<Product> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder("SELECT * FROM Products WHERE 1=1");

        if (categoryId != null) sb.append(" AND CategoryID = ").append(categoryId);
        if (material != null && !material.isEmpty()) sb.append(" AND Material LIKE '%").append(material).append("%'");
        if (gender != null && !gender.isEmpty()) sb.append(" AND Gender = '").append(gender).append("'");
        if (minPrice != null) sb.append(" AND Price >= ").append(minPrice);
        if (maxPrice != null) sb.append(" AND Price <= ").append(maxPrice);

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sb.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next())
                list.add(parseProduct(rs, c));

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    // ============================
    //          SORT
    // ============================
    public List<Product> sort(String sortBy) {
        String order = switch (sortBy) {
            case "price_asc" -> "ORDER BY Price ASC";
            case "price_desc" -> "ORDER BY Price DESC";
            case "name_asc" -> "ORDER BY ProductName ASC";
            case "name_desc" -> "ORDER BY ProductName DESC";
            case "newest" -> "ORDER BY CreatedAt DESC";
            default -> "";
        };

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM Products " + order;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next())
                list.add(parseProduct(rs, c));

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    // ============================
    //        THỐNG KÊ
    // ============================

    public int countProducts() {
        String sql = "SELECT COUNT(*) FROM Products";

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

    public Map<String, Integer> countByCategory() {
        Map<String, Integer> map = new HashMap<>();

        String sql = """
                SELECT c.CategoryName, COUNT(*)
                FROM Products p
                JOIN Categories c ON p.CategoryID=c.CategoryID
                GROUP BY c.CategoryName
                """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return map;
    }

    public Map<String, Integer> countStockStatus() {
        Map<String, Integer> map = new HashMap<>();

        String sql = """
                SELECT 
                    SUM(CASE WHEN v.Stock > 0 THEN 1 ELSE 0 END) AS InStock,
                    SUM(CASE WHEN v.Stock = 0 THEN 1 ELSE 0 END) AS OutStock
                FROM ProductVariants v
                """;

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                map.put("InStock", rs.getInt("InStock"));
                map.put("OutStock", rs.getInt("OutStock"));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return map;
    }

    // ======================================================
    //                   HELPER FUNCTIONS
    // ======================================================

    private Product parseProduct(ResultSet rs, Connection c) throws Exception {
        Product p = new Product();

        p.setProductId(rs.getInt("ProductID"));
        p.setCategoryId(rs.getInt("CategoryID"));
        p.setProductName(rs.getString("ProductName"));
        p.setMaterial(rs.getString("Material"));
        p.setWeight(rs.getDouble("Weight"));
        p.setGender(rs.getString("Gender"));
        p.setDescription(rs.getString("Description"));
        p.setPrice(rs.getBigDecimal("Price"));

        // Load images
        p.setImages(loadImages(p.getProductId(), c));

        // 👉 VERY IMPORTANT: Add category name field
        try {
            p.setCategoryName(rs.getString("CategoryName"));
        } catch (SQLException ex) {
            // If query does not contain CategoryName, ignore
        }

        return p;
    }


    private void fillStatement(PreparedStatement ps, Product p) throws Exception {
        ps.setInt(1, p.getCategoryId());
        ps.setString(2, p.getProductName());
        ps.setString(3, p.getMaterial());
        ps.setDouble(4, p.getWeight());
        ps.setString(5, p.getGender());
        ps.setString(6, p.getDescription());
        ps.setBigDecimal(7, p.getPrice());
    }

    private boolean existsInOrderDetails(int productId, Connection c) throws Exception {
        String sql = "SELECT 1 FROM OrderDetails WHERE ProductID=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void deleteImages(int productId, Connection c) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(
                "DELETE FROM ProductImages WHERE ProductID=?")) {

            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }

    private void insertImages(int productId, List<String> images, Connection c) throws Exception {
        if (images == null) return;

        for (String img : images) {
            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO ProductImages(ProductID, ImageURL) VALUES(?,?)")) {

                ps.setInt(1, productId);
                ps.setString(2, img);
                ps.executeUpdate();
            }
        }
    }

    private List<String> loadImages(int productId, Connection c) throws Exception {
        List<String> imgs = new ArrayList<>();

        try (PreparedStatement ps = c.prepareStatement(
                "SELECT ImageURL FROM ProductImages WHERE ProductID=?")) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) imgs.add(rs.getString(1));
            }
        }

        return imgs;
    }
}
