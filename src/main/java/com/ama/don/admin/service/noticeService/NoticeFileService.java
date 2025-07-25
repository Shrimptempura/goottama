package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.temp.FileIDao;
import com.ama.don.admin.utils.FileUtil;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TUI로 사진을 글 중간에 첨부 하는 경우 첨부 즉시 물리적으로 쓰여짐 <br/>
 * 하지만 공지를 삭제 하지 않는 한, 공지 수정 상황에서 해당 사진들은 물리적 삭제가 자동으로 이루어지지 않음. <br/>
 * 이 클래스는 그런 고아파일을 찾아 삭제하는 역할을 함.
 */
@Service
public class NoticeFileService {

    // tui 사진 저장경로 주입
    @Value("${file.upload-location:}")
    private String tuiEditorUploadLocation;

    private final FileIDao fileIDao;
    private final FileUtil fileUtil;

    public NoticeFileService(FileIDao fileIDao, FileUtil fileUtil){
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
    }

    /**
     * 고아파일 식별 및 동기화를 위한 메서드.<br/>
     * 공지 내용에서 현재 사용 하는 파일 이름을 추출 하고, 기존에 등록 된 파일 이름을 비교 해
     * 글 작성 완료 시점에 사용 중인 파일만 남기고 나머지는 삭제.
     * @param noticeId 공지 아이디
     * @param htmlContent 공지 내용
     */
    @Transactional
    public void processTuiEditorImages(Long noticeId, String htmlContent) {
        // 공지 내용에서 현재 사용 중인 이미지 파일 이름 추출
        List<String> usedImageFilenames = extractSavedFilenamesFromHtml(htmlContent);
        // 현재 게시물과 연결된 기존 TUI 에디터 이미지 파일 목록 조회
        List<FileDto> existingTuiFiles = fileIDao.getFilesByTargetAndUploader(TargetType.ADMIN, noticeId, "TUI_EDITOR");
        // 고아파일 식별 및 삭제
        System.out.println(">>> Extracted filenames: " + usedImageFilenames);
        for (FileDto existingFile : existingTuiFiles) {
            System.out.println(">>> Checking existing file for deletion: " + existingFile.getFile_path());
            if (!usedImageFilenames.contains(existingFile.getFile_path())) {
                fileUtil.deleteFile(existingFile.getFile_path(), existingFile.getFile_uploader()); // 물리적 삭제
                fileIDao.deleteFile(existingFile.getFile_id()); // DB에서 삭제
                System.out.println("고아 TUI 에디터 이미지 삭제됨: " + existingFile.getFile_path());
            } else {
                System.out.println(">>> File still in use, not deleting: " + existingFile.getFile_path());
            }
        }
        // 새로 추가되거나 기존에 연결되지 않은 임시 TUI 에디터 이미지 연결
        if (!usedImageFilenames.isEmpty()) {
            System.out.println(">>> noticeId : " + noticeId);
            System.out.println(">>> usedImageFilenames : " + usedImageFilenames);
            fileIDao.updateFilesTargetAndUploader(noticeId, usedImageFilenames);
            System.out.println("TUI 에디터 이미지 파일들 DB 연결 업데이트 완료!");
        }
    }

    /**
     * HTML 본문에서 TUI 에디터에 의해 업로드된 이미지의 저장된 파일명(UUID.확장자)을 정규 표현식으로 추출함.
     * @param htmlContent 공지사항 본문 HTML
     * @return 추출된 파일명 리스트
     */
    private List<String> extractSavedFilenamesFromHtml(String htmlContent) {
        List<String> filenames = new ArrayList<>();
        if (htmlContent == null || htmlContent.isEmpty()) {
            return filenames;
        }
        // TUI 에디터가 삽입하는 이미지 태그의 src 속성에서 파일명을 추출하는 정규식
        // /uploadedImages/ 다음에 오는 UUID 문자열과 확장자를 매칭
        Pattern pattern = Pattern.compile("<img\\s+src=\"/uploadedImages/([a-fA-F0-9]{32}\\.[a-zA-Z0-9]+)\"[^>]*?>");
        Matcher matcher = pattern.matcher(htmlContent);

        while (matcher.find()) {
            filenames.add(matcher.group(1));
        }
        return filenames;
    }

    /**
     * 공지 작성 중 중간에 삭제 한 사진 파일들을 일괄적으로 삭제하는 메서드.<br/><br/>
     * 공지 작성 중 중간에 삽입 하는 사진들은 음수의 `target_id`를 받음. 최종적으로 사용 되는 사진은 정상적인 `target_id`로 바뀌지만,
     * 중간에 삭제 되어서 사용되지 않는 사진들은 음수 `target_id`를 그대로 유지함. 이 사진들은 실시간으로 물리적 삭제가 쉽지 않아서 주기적으로 삭제 할 수 있도록 이 메서드를 만듦.
     * 실시간으로 삭제하지 못 하는 이유는 글쓰기와 관련된 데이터를 매 순간 DB로 보내고 가지고 오는 건 부담스러운 일이고, 그렇다고 작성 완료 순간에
     * 모든 음수 `target_id`파일들을 삭제 하는 경우에는, 다른 사람이 작성 중인 사진 파일도 삭제 되기 때문임.
     */
    public void removeNegativeTargetIdFiles(){
        List<FileDto> negativeTargetIdFiles = fileIDao.getNegativeTargetIdFiles("TUI_EDITOR");
        for (FileDto negativeFiles : negativeTargetIdFiles) {
            System.out.println(">>> Checking negative file for deletion: " + negativeFiles.getFile_path());
            if (negativeTargetIdFiles != null || !negativeTargetIdFiles.equals("")) {
                fileUtil.deleteFile(negativeFiles.getFile_path(), negativeFiles.getFile_uploader()); // 물리적 삭제
                fileIDao.deleteFile(negativeFiles.getFile_id()); // DB에서 삭제
                System.out.println("고아 TUI 에디터 이미지 삭제됨: " + negativeFiles.getFile_path());
            } else {
                System.out.println(">>> File still in use, not deleting: " + negativeFiles.getFile_path());
            }
        }
    }
}
