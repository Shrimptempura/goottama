package com.ama.don.admin.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 파일 시스템 관련 유틸리티 기능을 제공하는 클래스.<br/>
 * 파일 저장, 삭제, 파일 경로 처리 등 파일 시스템과의 상호작용을 위한 공통 로직 포함함.<br/>
 * 다양한 파일 처리 서비스에서 재사용됨.
 */
@Component
public class FileUtil {
    // 첨부파일 저장경로 주입
    @Value("${file.attachment-upload-location:}")
    private String attachmentUploadLocation;
    // tui 사진 저장경로 주입
    @Value("${file.upload-location:}")
    private String tuiEditorUploadLocation;

    // 디렉터리 생성
    public FileUtil(@Value("${file.attachment-upload-location:}") String uploadLoc) throws IOException {
        Path uploadPath = Paths.get(uploadLoc);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("첨부파일 업로드 디렉토리 생성됨: " + uploadPath.toAbsolutePath());
        }
    }

    /**
     * MultipartFile 객체를 받아 서버의 지정된 경로에 실제 파일로 저장함.<br/>
     * 파일명 충돌을 피하기 위해 UUID를 사용하여 고유한 파일명 생성함.
     *
     * @param file 저장할 {@link org.springframework.web.multipart.MultipartFile} 객체.
     * @return 서버에 저장된 고유한 파일명 반환됨.<br/>
     * 저장 실패 시 `null` 반환될 수 있음.
     * @throws IOException 파일 저장 중 입출력 오류 발생 시 발생함.
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
     * 서버의 실제 파일 시스템에서 파일을 삭제함.<br/>
     * 파일 경로를 받아 해당 파일을 물리적으로 제거함.
     *
     * @param savedFilename 삭제할 파일의 서버 절대 경로.
     * @param fileUploader file_uploader. 파일 결로 구분을 위함.
     * @return 삭제 성공 시 `true`, 실패 시 `false` 반환됨.<br/>
     * 파일이 존재하지 않거나 삭제 권한이 없는 경우 `false` 반환될 수 있음.
     */
    public boolean deleteFile(String savedFilename, String fileUploader) {
        String baseUploadLocation;
        if (fileUploader.equals("TUI_EDITOR")) {
            baseUploadLocation = tuiEditorUploadLocation;
        } else{
            baseUploadLocation = attachmentUploadLocation;
        }
        if (baseUploadLocation == null || baseUploadLocation.isEmpty()) {
            System.err.println("[FileUtil] 파일 삭제 실패: 업로드 위치가 설정되지 않음. Uploader: " + fileUploader);
            return false;
        }
        Path filePath = Paths.get(baseUploadLocation, savedFilename);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("파일 삭제 실패 : " + filePath.toAbsolutePath() + ", 오류: " + e.getMessage());
            return false;
        }
    }

    /**
     * 파일의 전체 경로를 포함하는 {@link java.nio.file.Path} 객체 반환함.<br/>
     * 파일 경로 문자열을 Path 객체로 변환하여 파일 시스템 작업에 용이하게 함.
     *
     * @param savedFilename Path 객체로 변환할 파일 경로 문자열.
     * @return 변환된 {@link java.nio.file.Path} 객체 반환됨.
     */
    public Path getFilePath(String savedFilename) {
        return Paths.get(attachmentUploadLocation, savedFilename);
    }
}
