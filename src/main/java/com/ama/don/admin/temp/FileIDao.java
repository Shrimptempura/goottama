package com.ama.don.admin.temp;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
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
     * @param FileDto 저장할 파일 DTO
     * @return 성공 시 true (영향받은 행의 수가 1이면 true)
     */
    boolean insertFile(FileDto FileDto);

    /**
     * 특정 target_type과 target_id에 속한 파일 목록을 조회한다.
     * @param targetType 대상 타입 ('ADMIN', 'COMMUNITY' 등)
     * @param targetId 대상 ID (예: notices_id)
     * @return 파일 목록
     */
    List<FileDto> getFilesByTarget(@Param("targetType") TargetType targetType, @Param("targetId") Long targetId);

    /**
     * 특정 파일을 ID로 조회한다.
     * @param fileId 파일 ID
     * @return FileDto (없으면 null)
     */
    FileDto getFileById(@Param("fileId") Long fileId);

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
    int deleteFilesByTarget(@Param("targetType") TargetType targetType, @Param("targetId") Long targetId);

    /**
     * TUI 에디터 이미지의 target_id를 업데이트
     * @param targetId 대상 ID
     * @param filePaths 변환된 업로드 전용 파일 이름
     * @return 임시로 지정한 음수 타겟 아이디가 정상적인 공지 아이디로 변환됨
     */
    int updateFilesTargetAndUploader(@Param("targetId") Long targetId,
                                     @Param("filePaths") List<String> filePaths);

    /**
     *
     * @param targetType 대상 타입
     * @param targetId 대상 ID
     * @param fileUploader 파일 업로더
     * @return 현재 게시물과 연결된 기존 TUI 에디터 이미지 파일 목록
     */
    List<FileDto> getFilesByTargetAndUploader(@Param("targetType") TargetType targetType, @Param("targetId") Long targetId,
                                              @Param("fileUploader") String fileUploader);

    /**
     * 임시파일 일괄 삭제를 위한 메서드.<br />
     * 현재 TUI 에디터에서 임시로 저장하는 파일들은 음수 target_id를 가짐.<br>
     * 주기적으로 NoticeFileService.removeNegativeTargetIdFiles를 이용해 사용되지 않는 임시 사진 파일들을 삭제 하는데 이용함
     * @param fileUploader TUI_EDITOR
     * @return target_id가 음수인 파일 데이터
     */
    public List<FileDto> getNegativeTargetIdFiles(String fileUploader);
}