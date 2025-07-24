package com.ama.don.admin.temp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 파일 정보 데이터베이스 접근을 위한 DAO(Data Access Object) 인터페이스.<br/>
 * 파일 정보(FileDto)를 조회, 삽입, 삭제하는 데이터 연산 메서드 정의함.<br/>
 * MyBatis 매퍼와 연동되어 파일 관련 데이터베이스 상호작용 처리함.
 */
@Repository
@Mapper
public interface  FileIDao {
    /**
     * 새로운 파일 정보를 저장한다.
     * @param tFileDto 저장할 파일 DTO
     * @return 성공 시 true (영향받은 행의 수가 1이면 true)
     */
    boolean insertFile(tFileDto tFileDto);

    /**
     * 특정 target_type과 target_id에 속한 파일 목록을 조회한다.
     * @param targetType 대상 타입 ('ADMIN', 'COMMUNITY' 등)
     * @param targetId 대상 ID (예: notices_id)
     * @return 파일 목록
     */
    List<tFileDto> getFilesByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    /**
     * 특정 파일을 ID로 조회한다.
     * @param fileId 파일 ID
     * @return FileDto (없으면 null)
     */
    tFileDto getFileById(@Param("fileId") Long fileId);

    /**
     * 특정 파일을 삭제한다. (DB에서만)
     * @param fileId 삭제할 파일 ID
     * @return 성공 시 true, 실패 시 false
     */
    boolean deleteFile(@Param("fileId") Long fileId);

    /**
     * 특정 target_type과 target_id에 속한 모든 파일을 삭제한다.
     * @param targetType 대상 타입
     * @param targetId 대상 ID
     * @return 삭제된 행의 수
     */
    int deleteFilesByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);
}