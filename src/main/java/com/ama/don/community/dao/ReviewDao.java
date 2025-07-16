package com.ama.don.community.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewDao {

	void Review(int postid, int userid, String posttitle, String postcontent, Timestamp postdate, int postcount,
			int postlike_count, String postimg);

}
