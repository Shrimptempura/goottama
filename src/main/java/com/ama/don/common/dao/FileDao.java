package com.ama.don.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;

import java.util.List;

@Mapper
public interface FileDao {

	// 파일 저장
	void create(FileDto fileDto);

	//회원파트에서 사용 파일 삭제 
	void deleteByTarget(@Param("targetType") TargetType type,@Param("targetId") long target_id);
	void deleteByUploader(String file_uploader);
	void deleteByTargetAndUploader(@Param("file_uploader")String uploader,@Param("targetType") TargetType type,@Param("targetId") long target_id);
	
	// targetType과 targetId를 이용한 리뷰 목록 조회
	List<FileDto> findByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

	//user_detail테이블 user_img삭제,추가
	void deleteProfileImg(String oldFileName);
	void createProfileImg(@Param("fileName")String fileName,@Param("user_id")long user_id);


	// 파일 삭제
	void delete(long file_id);

	void updateTargetId(@Param("targetType") TargetType targetType, @Param("fileUploader") String fileUploader,
			@Param("oldTargetId") Long oldTargetId, @Param("newTargetId") Long newTargetId);
	
	// 특정 타겟타입과 타겟ID로 파일 리스트 조회(커뮤니티)
	List<FileDto> findByTargetId(@Param("targetType") TargetType targetType, @Param("targetId") Long targetId);


}
