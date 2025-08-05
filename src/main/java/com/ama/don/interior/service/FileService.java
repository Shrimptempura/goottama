package com.ama.don.interior.service;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    // 생성
    void saveFile(Long userId, TargetType targetType, Long targetId, MultipartFile file);

    // 조회, findByTargetId
    List<FileDto> getFileList(Long userId, TargetType targetType, Long targetId);

    // 삭제, interiorFindById으로 단건 조회,  interiorDeletedById으로 단건 삭제
    void deleteFile(Long fileId);
}
