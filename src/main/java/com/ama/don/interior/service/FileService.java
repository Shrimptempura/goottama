package com.ama.don.interior.service;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
    // 생성(여러장은 반복)
    void saveFile(TargetType targetType, Long targetId, MultipartFile file, boolean isThumbnail);

    // 조회, 장수 상관없이 리스트
    List<FileDto> getFileList(TargetType targetType, Long targetId);

    // 단건 삭제
    void deleteFile(Long fileId);

    // 썸네일 1장 삭제
    void deleteThumbnail(TargetType targetType, Long targetId);

    // 타겟 일치 모두 삭제
    void deleteAllByTargetId(TargetType targetType, Long targetId);

    // 2쿼리 IN 사용할 썸네일만 조회하기
    Map<Long, FileDto> getThumbnailList(TargetType targetType, List<Long> targetIds);
}
