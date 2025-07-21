package com.ama.don.community.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
    private static final String URL = "jdbc:mariadb://localhost:3306/goott";
    private static final String USER = "blue";
    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null)
                    resource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
