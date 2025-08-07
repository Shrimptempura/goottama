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

	// 타겟 아이디 업데이트
	void updateTargetId(@Param("target_type") TargetType targetType, @Param("file_uploader") String fileUploader,
			@Param("old_target_id") Long oldTargetId, @Param("new_target_id") Long newTargetId);

	// 타겟타입과 타겟ID로 파일 리스트 조회(커뮤니티)
	List<FileDto> findByTargetId(@Param("target_type") TargetType targetType, @Param("target_id") Long targetId);

	// 유저가 업로드한 target_id가 없는 임시 이미지 삭제
	void deleteTempFiles(@Param("target_type") TargetType targetType, @Param("file_uploader") Long userId);

	// 인테리어사용, 단건 삭제
	int interiorDeletedById(@Param("fileId") Long fileId);

	// 인테리어사용, 단건 조회
	FileDto interiorFindById(@Param("fileId") Long fileId);

}
