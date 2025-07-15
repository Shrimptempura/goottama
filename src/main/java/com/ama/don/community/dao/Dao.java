package com.ama.don.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.ama.don.community.dto.Dto;
import com.tech.prjm09.util.DBCon;

public class Dao {
	Connection conn=null;
	public ArrayList<Dto> list() {
		ArrayList<Dto> dtos=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBCon.getConnection();
			String sql="select bid,bname,btitle,bcontent,"
					+ "bdate,bhit,bgroup,bstep,bindent "
					+ "from replyboard "
					+ "order by bgroup desc,bstep asc";
	
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				int bid=rs.getInt("bid");
				String bname=rs.getString("bname");
				String btitle=rs.getString("btitle");
				String bcontent=rs.getString("bcontent");
				Timestamp bdate=rs.getTimestamp("bdate");
				int bhit=rs.getInt("bhit");
				int bgroup=rs.getInt("bgroup");
				int bstep=rs.getInt("bstep");
				int bindent=rs.getInt("bindent");
				Dto dto=new Dto(bid, bname, btitle,
						bcontent, bdate, bhit, bgroup,
						bstep, bindent);
				dtos.add(dto);
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dtos;
	}
}
