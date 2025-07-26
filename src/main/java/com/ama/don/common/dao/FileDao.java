package com.ama.don.common.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.dto.ReviewDto;

@Mapper
public interface FileDao {
	
	//파일 저장
	void create(FileDto fileDto);
	
	//파일 삭제
	void delete(long file_id);
	
	// targetType과 targetId를 이용한 리뷰 목록 조회
	ArrayList<FileDto> findByTarget(String targetType, Long targetId);

}
