package com.ama.don.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.common.dto.FileDto;

@Mapper
public interface FileDao {
	
	// 파일 업로드
	void insertFile(FileDto fileDto);

	// 파일 저장
	void create(FileDto fileDto);

	// 파일 삭제
	void delete(long file_id);

	// targetType과 targetId를 이용한 리뷰 목록 조회
	List<FileDto> findFilesByTarget(String targetType, Long targetId);

}
