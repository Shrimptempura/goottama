package com.ama.don.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * TUI 에디터 이미지 업로드 전용 컨트롤러.
 * 다운로드는 `AttachmentController`에서, <br/>
 * 에디터 속 이미지 출력은 `WebConfig`가 하고 있음.
 */
@RestController
@RequestMapping("/tui-editor")
public class FileApiController {
    // 파일 업로드 할 디렉터리 경로
    @Value("${file.upload-location:}")
    private String uploadDir;

    /**
     * 에디터 이미지 업로드
     * @param image 파일 객체
     * @return 웹에서 접근할 수 있는 URL 반환
     */
    @PostMapping("/image-upload")
    public String uploadEditorImage(@RequestParam final MultipartFile image) {
        if (image.isEmpty()) {
            System.out.println("업로드된 파일이 비어있습니다.");
            return ""; // 빈 파일은 빈 문자열 반환
        }

        String orgFilename = image.getOriginalFilename();
        System.out.println("원본 파일명 (orgFilename): " + orgFilename);
        System.out.println("원본 파일명 길이: " + orgFilename.length());

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = "";
        int lastDotIndex = orgFilename.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < orgFilename.length() - 1) {
            extension = orgFilename.substring(lastDotIndex + 1);
        }
        String saveFilename = uuid; // 확장자가 없을 경우를 대비하여 일단 uuid만으로 초기화
        if (!extension.isEmpty()) {
            saveFilename += "." + extension; // 확장자가 있을 경우에만 추가
        }
        // 파일의 전체 경로 (업로드 디렉토리 + 파일명)
        String fileFullPath = Paths.get(uploadDir, saveFilename).toString();
        System.out.println("saveFilename : " + saveFilename);
        System.out.println("fileFullPath : " + fileFullPath);

        try {
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile); // 파일 저장
            // 클라이언트에게 반환할 URL (WebConfig에서 /uploadedImages/** 로 매핑했기 때문)
            return "/uploadedImages/" + saveFilename; // 웹에서 접근할 수 있는 URL 반환
        } catch (IOException e){
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
    }

    /**
     * 디스크에 업로드된 파일을 byte[]로 반환
     * @param filename 디스크에 업로드된 파일명
     * @return image byte array
     * @deprecated - WebConfig의 도입으로 실제로는 사용되지 않음.
     */
    @GetMapping(value = "/image-print", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] printEditorImage(@RequestParam final String filename) {
        // 실제 저장된 파일의 경로 (업로드 디렉토리 + 파일명)
        String fileFullPath = Paths.get(uploadDir, filename).toString();

        File uploadedFile = new File(fileFullPath);
        if (!uploadedFile.exists()) {
            throw new RuntimeException("파일 찾을 수 없음 : " + filename);
        }

        try {
            byte[] imageBytes = Files.readAllBytes((uploadedFile.toPath()));
            return imageBytes;

        } catch (IOException e){
            throw new RuntimeException("이미지 로드 중 오류 발생", e);
        }
    }
}
