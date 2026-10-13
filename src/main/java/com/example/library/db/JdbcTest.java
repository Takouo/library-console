package com.example.library.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/library_db"
                + "?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
        String user = "root";
        String password = "13949203498";

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("连接成功: " + conn);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM books");

        if (rs.next()) {
            System.out.println("books 表共有 " + rs.getInt(1) + " 条记录");
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}