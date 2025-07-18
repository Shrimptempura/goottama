package com.ama.don.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ama.don.community.dto.Review_viewDto;
import com.ama.don.community.util.DBCon;

public class Review_viewDao {
	Connection conn = null;

	public ArrayList<Review_viewDto> review_view() {
		ArrayList<Review_viewDto> dtos = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBCon.getConnection();
			String sql = "SELECT post_id, user_id, post_title, post_content, post_count, post_like_count, post_img FROM review_view";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int post_id = rs.getInt("post_id");
				int user_id = rs.getInt("user_id");
				String post_title = rs.getString("post_title");
				String post_content = rs.getString("post_content");
				int post_count = rs.getInt("post_count");
				int post_like_count = rs.getInt("post_like_count");
				String post_img = rs.getString("post_img");

				Review_viewDto dto = new Review_viewDto(post_id, user_id, post_title, post_content, post_count,
						post_like_count, post_img);

				dtos.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace(); // 꼭 예외는 출력해서 확인하세요
		}

		return dtos;
	}
}
