package com.ama.don.admin.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {
    // 첨부파일 저장경로 주입
    @Value("${file.attachment-upload-location}")
    private String attachmentUploadLocation;

    // 디렉터리 생성
    public FileUtil(@Value("${file.attachment-upload-location}") String uploadLoc) throws IOException {
        Path uploadPath = Paths.get(uploadLoc);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("첨부파일 업로드 디렉토리 생성됨: " + uploadPath.toAbsolutePath());
        }
    }

    /**
     * MultipartFile을 지정된 경로에 저장하고 저장된 파일명을 반환합니다.
     * @param file 저장할 MultipartFile
     * @return 서버에 저장된 파일명 (UUID.확장자)
     * @throws IOException 파일 저장 중 오류 발생 시
     */
    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = "";
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < originalFilename.length() -1) {
            extension = originalFilename.substring(lastDotIndex + 1);
        }
        String savedFilename = uuid;
        if (!extension.isEmpty()) {
            savedFilename += "." + extension;
        }
        Path targetLocation = Paths.get(attachmentUploadLocation, savedFilename);
        Files.copy(file.getInputStream(), targetLocation);

        System.out.println("파일 저장 완료 : " + targetLocation.toAbsolutePath());
        return savedFilename;
    }

    /**
     * 지정된 경로의 파일을 삭제합니다.
     * @param savedFilename 서버에 저장된 파일명
     * @return 삭제 성공 시 true, 실패 시 false
     */
    public boolean deleteFile(String savedFilename) {
        Path filePath = Paths.get(attachmentUploadLocation, savedFilename);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("파일 삭제 실패 : " + filePath.toAbsolutePath() + ", 오류: " + e.getMessage());
            return false;
        }
    }

    /**
     * 파일의 전체 경로를 반환합니다.
     * @param savedFilename 서버에 저장된 파일명
     * @return 파일의 절대 경로 Path 객체
     */
    public Path getFilePath(String savedFilename) {
        return Paths.get(attachmentUploadLocation, savedFilename);
    }
}
