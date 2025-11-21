package org.example.aurorajewelry.util;

import java.sql.*;

public class DBUtil {

    private static final String DB_URL =
            "jdbc:sqlserver://localhost:1433;databaseName=JewelryStore;encrypt=false";

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy JDBC Driver SQL Server");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try { ac.close(); } catch (Exception ignored) {}
        }
    }
}
