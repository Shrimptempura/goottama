package com.ama.don.community.dao;

import org.apache.ibatis.annotations.Mapper;
import com.ama.don.community.dto.Write_viewDto;

@Mapper
public interface Write_viewDao {
	void insertPost(Write_viewDto dto);
}
