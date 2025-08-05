package com.ama.don.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;

@Mapper
public interface FileDao {

	// 파일 저장
	void create(FileDto fileDto);

	// 파일 삭제
	void delete(long file_id);

	void updateTargetId(@Param("targetType") TargetType targetType, @Param("fileUploader") String fileUploader,
			@Param("oldTargetId") Long oldTargetId, @Param("newTargetId") Long newTargetId);
	
	// 특정 타겟타입과 타겟ID로 파일 리스트 조회(커뮤니티)
	List<FileDto> findByTargetId(@Param("targetType") TargetType targetType, @Param("targetId") Long targetId);

}
