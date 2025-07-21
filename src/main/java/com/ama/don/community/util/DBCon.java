package com.ama.don.community.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon {
	static Connection con = null;

	public static Connection getConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://localhost:3306/goott";
			String user = "blue"; // DB 사용자 계정
			String pass = "123456"; // 비밀번호
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
