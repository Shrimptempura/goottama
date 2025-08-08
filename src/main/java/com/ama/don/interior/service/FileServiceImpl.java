package com.ama.don.interior.service;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;

    @Value("${file.upload.interior}")
    private String uploadBaseDir;

    @Override
    public void saveFile(TargetType targetType, Long targetId, MultipartFile file, boolean isThumbnail) {
        if (file == null || file.isEmpty()) {
            log.warn("FileService - 업로드 실패 - targetType: {}, targetId: {}", targetType, targetId);
            throw new IllegalArgumentException("빈 파일입니다.");
        }

        log.info("FileService - 파일 업로드 시작 - targetType: {}, targetId: {}", targetType, targetId);
        String originalName = file.getOriginalFilename();
        String saveDir = uploadBaseDir + "/" + targetType.name().toLowerCase();
        String savedName = UUID.randomUUID() + "_" + originalName;

        // 경로는 사전에 무조건 존재해야함, 따로 생성 코드는 없음
        File checkDir = new File(saveDir);
        if (!checkDir.exists()) {
            log.error("FileService - 로컬 디렉토리 생성 실패 - dir: {}", saveDir);
            throw new RuntimeException("로컬 디렉토리 생성 실패: " + saveDir);
        }

        File filePath = new File(saveDir, savedName);

        try {
            file.transferTo(filePath);
            log.info("FileService - 파일 저장 성공 - 경로: {}", filePath.getAbsolutePath());
        } catch (IOException e) {
            log.error("FileService - 파일 저장 실패 - 경로: {}, 에러: {}", filePath.getAbsolutePath(), e);
            throw new RuntimeException("파일 저장 실패", e);
        }

        FileDto fileDto = new FileDto();
        fileDto.setFile_uploader("interior");
        fileDto.setFile_name(savedName);
        fileDto.setFile_path(saveDir);
        fileDto.setTarget_type(targetType);
        fileDto.setTarget_id(targetId);
        fileDto.setThumbnail(isThumbnail);

        fileDao.interiorCreate(fileDto);
        log.info("FileService - DB 저장 완료 - fileDto: {}", fileDto);
    }

    @Override
    public List<FileDto> getFileList(TargetType targetType, Long targetId) {
        log.info("FileService - 파일 조회 요청 - targetType: {}, targetId: {}", targetType, targetId);
        return fileDao.findByTargetId(targetType, targetId);
    }

    // 삭제, 여러장은 iter로 돌림
    @Override
    public void deleteFile(Long fileId) {
        log.info("FileService - 파일 삭제 요청 - fileId: {}", fileId);
        FileDto fileDto = fileDao.interiorFindById(fileId);

        if (fileDto == null) {
            log.warn("FileService - 파일 삭제 실패 - 해당 파일 없음 - fileId: {}", fileId);
            throw new IllegalArgumentException("삭제 파일을 찾지 못했습니다.");
        }

        File realPhysical = new File(fileDto.getFile_path(), fileDto.getFile_name());
        if (realPhysical.exists()) {
            boolean isDeleted = realPhysical.delete();
            log.info("FileService - 실제 파일 삭제 - 경로: {}, 성공 여부: {}", realPhysical.getAbsolutePath(), isDeleted);
        } else {
            log.warn("FileService - 실제 파일이 존재하지 않음 - 경로: {}", realPhysical.getAbsolutePath());
        }

        int result = fileDao.interiorDeletedById(fileId);
        log.info("FileService - DB 삭제 결과 - fileId: {}, 삭제된 행 수 result: {}", fileId, result);
    }

    // 썸네일 1장 삭제
    @Override
    public void deleteThumbnail(TargetType targetType, Long targetId) {
        
    }

    // 타겟으로 모두 삭제
    @Override
    public int deleteAllByTargetId(TargetType targetType, Long targetId) {
        return 0;
    }
}
