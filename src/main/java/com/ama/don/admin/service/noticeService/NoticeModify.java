package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.common.dto.FileDto;
import com.ama.don.admin.temp.FileIDao;
import com.ama.don.admin.utils.FileUtil;
import com.ama.don.common.enums.TargetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * 기존 공지사항을 수정하고 데이터베이스에 반영하는 비즈니스 로직을 수행함.<br/>
 * 수정된 공지 내용, 고정 여부 외에 첨부파일 삭제 및 새로운 파일 추가 처리를 모두 포함함.<br/>
 * 모든 변경 사항은 단일 트랜잭션으로 처리되어 데이터 일관성 보장함.
 */
@Service
public class NoticeModify implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;
    private final FileIDao fileIDao;
    private final FileUtil fileUtil;
    private final TUIImageControlService tUIImageControlService;

    public NoticeModify(NoticesIDao noticesIDao, FileIDao fileIDao, FileUtil fileUtil, TUIImageControlService tUIImageControlService) {
        this.noticesIDao = noticesIDao;
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
        this.tUIImageControlService = tUIImageControlService;
    }

    /**
     * 공지사항 수정 작업을 실행함.<br/>
     * 모델에서 수정될 공지 정보({@link com.ama.don.admin.dto.NoticesDto}),<br/>
     * 삭제할 첨부파일 ID 목록({@link java.util.List}<{@link java.lang.Long}>),<br/>
     * 그리고 새로 추가될 파일 데이터({@link org.springframework.web.multipart.MultipartHttpServletRequest})를 받아 처리함.<br/>
     * 공지사항 본문 수정, 기존 파일 삭제, 새 파일 저장 순으로 작업 진행됨.
     *
     * @param model Spring UI Model. 수정될 공지 정보, 삭제할 파일 ID, 새로운 파일 데이터를 포함하며,<br/>
     * 처리 결과(`modifyResult`)를 모델에 추가하는 데 사용됨.
     * @throws RuntimeException 공지사항 수정 또는 파일 처리 중 오류 발생 시 발생함.
     */
    @Override
    @Transactional
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        NoticesDto modifiedNotice = (NoticesDto) map.get("modifiedNotice");
        List<Long> deleteFileIds = (List<Long>) map.get("deleteFileIds");
        System.out.println("DEBUG: deleteFileIds received in ModifyNotice service: " + deleteFileIds);
        MultipartHttpServletRequest mtfRequest = (MultipartHttpServletRequest) map.get("mtfRequest");
        boolean result = false;
        int noticesId = modifiedNotice.getNotices_id();
        String noticeContent = modifiedNotice.getNotices_content();
        result = noticesIDao.modifyNotice(modifiedNotice);
        if (!result) {
            throw new RuntimeException("공지사항 수정 실패 : " + noticesId);
        }
        
        // 첨부파일 삭제
        if (deleteFileIds != null && !deleteFileIds.isEmpty()) {
            for (Long fileId : deleteFileIds) {
                FileDto fileToDelete = fileIDao.getFileById(fileId);
                if (fileToDelete != null) {
                    boolean fileDeleted = fileUtil.deleteFile(fileToDelete.getFile_path(), fileToDelete.getFile_uploader());
                    if (fileDeleted) {
                        fileIDao.deleteFile(fileId);
                        System.out.println("첨부 파일 삭제 성공 : " + fileToDelete.getFile_name());
                    } else {
                        System.out.println("첨부 파일 삭제 실패 : " + fileToDelete.getFile_name());
                    }
                }
            }
        }
        // 새 첨부파일 추가
        List<MultipartFile> newAttachedFiles = mtfRequest.getFiles("attachedFiles");
        Long currentNoticeId = (long) modifiedNotice.getNotices_id();

        for (MultipartFile file : newAttachedFiles) {
            if (!file.isEmpty()) {
                try {
                    String savedFilename = fileUtil.saveFile(file);
                    if (savedFilename != null) {
                        FileDto fileDto = new FileDto();
                        fileDto.setTarget_type(TargetType.ADMIN);
                        fileDto.setTarget_id(currentNoticeId);
                        fileDto.setFile_name(file.getOriginalFilename());
                        fileDto.setFile_path(savedFilename);
                        fileDto.setFile_uploader("관리자");

                        fileIDao.insertFile(fileDto);
                    }
                } catch (Exception e) {
                    System.err.println("새 첨부파일 저장 중 오류 발생: " + file.getOriginalFilename());
                    e.printStackTrace();
                    throw new RuntimeException("새 첨부파일 저장 중 오류 발생", e);
                }
            }
        }
        tUIImageControlService.processTuiEditorImages((long)noticesId, noticeContent);
        System.out.println(">>> " + noticeContent);
        model.addAttribute("modifyResult", result);
        System.out.println("공지 수정 및 첨부파일 수정 성공 : " + currentNoticeId);
    }
}
