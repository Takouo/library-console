package com.example.library.db;

import java.sql.*;

public class JdbcCrud {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db"
            + "?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "13949203498";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // 1. 插入
            String insertSql = "INSERT INTO books (isbn, title, author, borrowed) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, "978-7-111-9");
                ps.setString(2, "重构");
                ps.setString(3, "Martin Fowler");
                ps.setInt(4, 0);
                int rows = ps.executeUpdate();
                System.out.println("插入 " + rows + " 行");
            }

            // 2. 查询
            String selectSql = "SELECT isbn, title, author, borrowed FROM books WHERE author = ?";
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setString(1, "Bruce Eckel");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.printf("%s | %s | %s | %s%n",
                                rs.getString("isbn"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getInt("borrowed") == 1 ? "已借出" : "可借");
                    }
                }
            }

            // 3. 更新
            String updateSql = "UPDATE books SET borrowed = ? WHERE isbn = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, 1);
                ps.setString(2, "978-7-111-9");
                System.out.println("更新 " + ps.executeUpdate() + " 行");
            }

            // 4. 删除
            String deleteSql = "DELETE FROM books WHERE isbn = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setString(1, "978-7-111-9");
                System.out.println("删除 " + ps.executeUpdate() + " 行");
            }
        }
    }
}