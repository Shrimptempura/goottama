package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.temp.tFileDto;
import com.ama.don.admin.temp.FileIDao;
import com.ama.don.admin.utils.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 공지사항 삭제 비즈니스 로직을 수행하는 서비스 구현체.<br/>
 * 특정 공지사항을 삭제할 때, 해당 공지사항과 연관된 모든 첨부파일 및
 * TUI 에디터로 삽입된 이미지 파일들을 함께 물리적으로, 그리고 DB에서 삭제함.<br/>
 * 트랜잭션 관리({@code @Transactional})를 통해 데이터 일관성 보장함.
 */
@Service
public class NoticeDelete implements NoticeServiceInterface{

    @Value("${file.upload-location:}") // C:\tui-editor-uploads
    private String tuiEditorUploadLocation;

    private final NoticesIDao noticesIDao;
    private final FileIDao fileIDao;
    private final FileUtil fileUtil;

    public NoticeDelete(NoticesIDao noticesIDao, FileIDao fileIDao, FileUtil fileUtil) {
        this.noticesIDao = noticesIDao;
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
    }

    /**
     * 공지사항 삭제 작업을 실행함.<br/>
     * 요청에서 공지 ID를 받아, 해당 공지 내용 내의 TUI 에디터 이미지,
     * 일반 첨부파일 정보 및 실제 파일, 마지막으로 공지사항 본문까지 모두 삭제함.<br/>
     * 모든 삭제 작업은 하나의 트랜잭션으로 처리됨.
     *
     * @param model Spring UI Model. 요청 정보(HttpServletRequest)를 받아오고,
     * 삭제 결과를 모델에 추가하는 데 사용됨.
     * @throws RuntimeException 삭제할 공지사항을 찾을 수 없거나,<br/>
     * 파일 삭제, DB 정보 삭제, 공지사항 본문 삭제 중 오류 발생 시 발생함.
     */
    @Override
    @Transactional
    public void execute(Model model) {
        boolean result = false;
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String noticeId = request.getParameter("notices_id");
        Long noticeIdLong = Long.parseLong(noticeId);

        // 공지 내의 사진 추적을 위해 삭제될 공지 내용 조회
        NoticesDto noticeToDelete = noticesIDao.getNoticeById(noticeId);
        if (noticeToDelete == null) {
            throw new RuntimeException("삭제할 공지사항을 찾을 수 없습니다. ID: " + noticeId);
        }
        // 공지사항에 삽입된 이미지 파일 삭제
        String content = noticeToDelete.getNotices_content();
        if (content != null && !content.isEmpty()) {
            Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+\\/([^'\"]+\\.(?:png|jpg|jpeg|gif|bmp|webp)))['\"][^>]*>");
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                String fullPath = matcher.group(1);
                String filename = matcher.group(2);

                File imageFile = new File(tuiEditorUploadLocation, filename);
                if (imageFile.exists() && imageFile.delete()) {
                    System.out.println("DEBUG: TUI 에디터 이미지 물리적 삭제 성공: " + imageFile.getAbsolutePath());
                } else {
                    System.err.println("ERROR: TUI 에디터 이미지 물리적 삭제 실패 또는 파일 없음: " + imageFile.getAbsolutePath());
                }
            }
        }
        // 해당 공지사항에 연결된 첨부파일 조회
        List<tFileDto> attachedFiles = fileIDao.getFilesByTarget("ADMIN", noticeIdLong);
        // 파일 물리적 삭제
        if (attachedFiles != null && !attachedFiles.isEmpty()) {
            for (tFileDto file : attachedFiles) {
                boolean fileDeleted = fileUtil.deleteFile(file.getFile_path());
                if (fileDeleted) {
                    System.out.println("DEBUG: 물리적 파일 삭제 성공: " + file.getFile_path());
                } else {
                    System.err.println("ERROR: 물리적 파일 삭제 실패: " + file.getFile_path());
                    // 오류 발생 시에도 DB 삭제는 시도하는 것이 일반적 (더 이상 접근 불가하므로)
                }
            }
        }
        // DB 동기화
        int filesDeletedCount = fileIDao.deleteFilesByTarget("ADMIN", noticeIdLong);
        System.out.println("DEBUG: DB에서 첨부파일 정보 " + filesDeletedCount + "개 삭제 완료 (target_id=" + noticeId + ")");

        // 공지사항 본문 삭제
        int noticeDeleted = noticesIDao.deleteNotice(noticeId);

        result = noticeDeleted > 0;
        if (!result) {
            throw new RuntimeException("공지사항 삭제 실패! ID: " + noticeId);
        }

        model.addAttribute("deleteResult", result);
    }
}
