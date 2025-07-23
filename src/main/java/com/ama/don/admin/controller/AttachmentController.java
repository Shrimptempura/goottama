package com.ama.don.admin.controller;

import com.ama.don.admin.temp.FileDto;
import com.ama.don.admin.temp.FileIDao;
import com.ama.don.admin.utils.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Controller
@RequestMapping("/admin/attachments")
public class AttachmentController {

    private final FileIDao fileIDao;
    private final FileUtil fileUtil;

    public AttachmentController(FileIDao fileIDao, FileUtil fileUtil) {
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadAttachment(@RequestParam("fileId") Long fileId) {
        try {
            // DB에서 파일 정보 조회
            FileDto fileDto = fileIDao.getFileById(fileId);
            if (fileDto == null) {
                throw new FileNotFoundException("파일 정보를 찾을 수 없음");
            }
            // 파일 경로 가져오기
            Path filePath = fileUtil.getFilePath(fileDto.getFile_path()); // file_path는 서버에 저장된 이름임
            Resource resource = new UrlResource(filePath.toUri());
            // 파일 존재 확인
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("파일을 찾을 수 없음");
            }
            // Content-Disposition 헤더 설정 (다운로드될 파일명은 original_filename)
            String encodedOriginalFilename = UriUtils.encode(fileDto.getFile_name(), StandardCharsets.UTF_8.toString());
            String contentDisposition = "attachment; filename=\"" + encodedOriginalFilename + "\"";
            // ResponseEntity 반환
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (MalformedURLException e) {
            System.err.println("잘못된 파일 URL: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FileNotFoundException e) {
            System.err.println("파일 또는 파일 정보 없음: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (IOException e) {
            System.err.println("파일 다운로드 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
