package com.ama.don.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ama.don.community.dto.Review_viewDto;
import com.ama.don.community.util.DBCon;

public class Review_viewDao {

    // 게시글 조회
    public Review_viewDto findById(int post_id) {
        Review_viewDto dto = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBCon.getConnection();
            String sql = "SELECT * FROM post WHERE post_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post_id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new Review_viewDto();
                dto.setPost_id(rs.getInt("post_id"));
                dto.setUser_id(rs.getInt("user_id"));
                dto.setPost_title(rs.getString("post_title"));
                dto.setPost_content(rs.getString("post_content"));
                dto.setPost_date(rs.getTimestamp("post_date"));
                dto.setPost_count(rs.getInt("post_count"));
                dto.setPost_like_count(rs.getInt("post_like_count"));
                dto.setPost_img(rs.getString("post_img"));
                dto.setTarget_type(rs.getInt("target_type"));
                dto.setTarget_id(rs.getInt("target_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBCon.close(rs, pstmt, conn);
        }

        return dto;
    }

    // 조회수 증가
    public void increaseViewCount(int post_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBCon.getConnection();
            String sql = "UPDATE post SET post_count = post_count + 1 WHERE post_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post_id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBCon.close(pstmt, conn);
        }
    }

    // 전체 게시글 조회
    public ArrayList<Review_viewDto> review_view() {
        ArrayList<Review_viewDto> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBCon.getConnection();
            String sql = "SELECT * FROM post ORDER BY post_id DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Review_viewDto dto = new Review_viewDto(
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("post_title"),
                    rs.getString("post_content"),
                    rs.getTimestamp("post_date"),
                    rs.getInt("post_count"),
                    rs.getInt("post_like_count"),
                    rs.getString("post_img")
                );
                dto.setTarget_type(rs.getInt("target_type"));
                dto.setTarget_id(rs.getInt("target_id"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBCon.close(rs, pstmt, conn);
        }

        return list;
    }
}
