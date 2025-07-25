package com.ama.don.admin.controller;

import com.ama.don.common.dto.FileDto;
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

/**
 * 첨부파일과 관련된 HTTP 요청을 처리하는 컨트롤러.<br/>
 * 파일 업로드 후 서버에 저장된 TUI 에디터 이미지의 삭제 및<br/>
 * 공지사항에 첨부된 파일의 다운로드 요청을 담당함.
 */
@Controller
@RequestMapping("/admin/attachments")
public class AttachmentController {

    private final FileIDao fileIDao;
    private final FileUtil fileUtil;

    public AttachmentController(FileIDao fileIDao, FileUtil fileUtil) {
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
    }

    /**
     * 첨부파일 다운로드 요청 처리함.<br/>
     * 요청된 `fileId`를 통해 데이터베이스에서 파일 정보를 조회하고,<br/>
     * 서버에 저장된 실제 파일을 찾아 HTTP 응답 스트림으로 클라이언트에 전송함.<br/>
     * 파일 다운로드 시 원본 파일명과 MIME 타입을 정확하게 설정함.
     *
     * @param fileId 다운로드할 첨부파일의 고유 ID.
     * @return 파일 다운로드를 위한 {@link org.springframework.http.ResponseEntity} 객체 반환됨.<br/>
     * - 성공 시: HTTP 200 OK 상태와 함께 파일 데이터 및 관련 헤더(Content-Disposition, Content-Type) 포함됨.<br/>
     * - 파일 정보 없음: HTTP 404 Not Found 상태 반환됨.<br/>
     * - URL 형식 오류: HTTP 400 Bad Request 상태 반환됨.<br/>
     * - 기타 서버 오류: HTTP 500 Internal Server Error 상태 반환됨.
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadAttachment(@RequestParam("fileId") Long fileId) {
        try {
            // DB에서 파일 정보 조회
            FileDto FileDto = fileIDao.getFileById(fileId);
            if (FileDto == null) {
                throw new FileNotFoundException("파일 정보를 찾을 수 없음");
            }
            // 파일 경로 가져오기
            Path filePath = fileUtil.getFilePath(FileDto.getFile_path()); // file_path는 서버에 저장된 이름임
            Resource resource = new UrlResource(filePath.toUri());
            // 파일 존재 확인
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("파일을 찾을 수 없음");
            }
            // Content-Disposition 헤더 설정 (다운로드될 파일명은 original_filename)
            String encodedOriginalFilename = UriUtils.encode(FileDto.getFile_name(), StandardCharsets.UTF_8.toString());
            String contentDisposition = "attachment; filename=\"" + encodedOriginalFilename + "\"";
            // ResponseEntity 반환
            return ResponseEntity.ok() // 200
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // 이진 파일임을 알려주는 MIME 타입
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition) // 파일명 지정 헤더
                    .body(resource); // 실제 파일 데이터 (Resource)
        } catch (MalformedURLException e) {
            System.err.println("잘못된 파일 URL: " + e.getMessage());
            return ResponseEntity.badRequest().build(); // 400
        } catch (FileNotFoundException e) {
            System.err.println("파일 또는 파일 정보 없음: " + e.getMessage());
            return ResponseEntity.notFound().build(); // 404
        }catch (IOException e) {
            System.err.println("파일 다운로드 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().build(); // 500
        }
    }
}
