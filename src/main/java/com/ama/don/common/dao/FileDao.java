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

	// 타겟 아이디 업데이트
	void updateTargetId(@Param("target_type") TargetType targetType, @Param("file_uploader") String fileUploader,
			@Param("old_target_id") Long oldTargetId, @Param("new_target_id") Long newTargetId);

	// 타겟타입과 타겟ID로 파일 리스트 조회(커뮤니티)
	List<FileDto> findByTargetId(@Param("target_type") TargetType targetType, @Param("target_id") Long targetId);

	// 유저가 업로드한 target_id가 없는 임시 이미지 삭제
	void deleteTempFiles(@Param("target_type") TargetType targetType, @Param("file_uploader") Long userId);

}
