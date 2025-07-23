package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.temp.FileDto;
import com.ama.don.admin.temp.FileIDao;
import com.ama.don.admin.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@Service
public class NoticeModify implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;
    private final FileIDao fileIDao;
    private final FileUtil fileUtil;

    public NoticeModify(NoticesIDao noticesIDao, FileIDao fileIDao, FileUtil fileUtil) {
        this.noticesIDao = noticesIDao;
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
    }

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
        result = noticesIDao.modifyNotice(modifiedNotice);
        if (!result) {
            throw new RuntimeException("공지사항 수정 실패 : " + noticesId);
        }
        
        // 첨부파일 삭제
        if (deleteFileIds != null && !deleteFileIds.isEmpty()) {
            for (Long fileId : deleteFileIds) {
                FileDto fileToDelete = fileIDao.getFileById(fileId);
                if (fileToDelete != null) {
                    boolean fileDeleted = fileUtil.deleteFile(fileToDelete.getFile_path());
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
                        fileDto.setTarget_type("ADMIN");;
                        fileDto.setTarget_id(currentNoticeId);;
                        fileDto.setFile_name(file.getOriginalFilename());;
                        fileDto.setFile_path(savedFilename);;
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
        model.addAttribute("modifyResult", result);
        System.out.println("공지 수정 및 첨부파일 수정 성공 : " + currentNoticeId);
    }
}
