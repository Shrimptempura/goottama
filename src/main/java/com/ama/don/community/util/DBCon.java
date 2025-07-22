package com.ama.don.community.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon {
    static Connection con = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://localhost:3306/goott";
            String user = "blue";
            String pass = "123456";
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace(); // 오류 출력
        }
        return con;
    }
}