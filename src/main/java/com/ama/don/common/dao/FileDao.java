package com.ama.don.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;

import java.util.List;

@Mapper
public interface FileDao {

	// 파일 저장
	void create(FileDto fileDto);

	// 파일 삭제
	void delete(long file_id);

	// 임시 target_id를 다른 id로 변경
	void update_target_id(@Param("target_type") TargetType target_type, @Param("old_target_id") Long old_target_id,
			@Param("new_target_id") Long new_target_id);

	// target_type과 target_id를 이용한 리뷰 목록 조회
	List<FileDto> findByTarget(@Param("target_type") String target_type, @Param("target_id") Long target_id);

}
