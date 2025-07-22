package com.ama.don.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ama.don.community.util.DBCon;

public class Write_viewDao {

	public void write(String post_title, String post_content, String post_img) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBCon.getConnection();
			String query = "insert into post(post_id, user_id, post_title"
					+ "post_content, post_date, post_count, post_like_count, post_img, target_type, target_id)"
					+ "values(post_seq.nextval,0,0,?,?,sysdate,0,0,?,0,0)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, post_title);
			pstmt.setString(2, post_content);
			pstmt.setString(3, post_img);

			int rn = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}