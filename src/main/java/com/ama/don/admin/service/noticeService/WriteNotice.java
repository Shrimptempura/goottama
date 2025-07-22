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

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class WriteNotice implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;
    private final FileIDao fileIDao;
    private final FileUtil fileUtil;

    public WriteNotice(NoticesIDao noticesIDao, FileIDao fileIDao, FileUtil fileUtil) {
        this.noticesIDao = noticesIDao;
        this.fileIDao = fileIDao;
        this.fileUtil = fileUtil;
    }

    @Override
    @Transactional
    public void execute(Model model) {
        Map<String, Object> map=model.asMap();
        MultipartHttpServletRequest mtfRequest = (MultipartHttpServletRequest) map.get("mtfRequest");
        NoticesDto newNotice = (NoticesDto) map.get("newNotice");
        boolean result = false;
        result = noticesIDao.writeNotice(newNotice);
        if (result) {
            System.out.println("공지사항 DB 저장 성공!");
        } else {
            System.err.println("공지사항 DB 저장 실패!");
        }

        Long savedNoticeId = (long) newNotice.getNotices_id();
        List<MultipartFile> attachedFiles = mtfRequest.getFiles("attachedFiles");

        for (MultipartFile file : attachedFiles) {
            if(!file.isEmpty()){
                try {
                    String savedFilename = fileUtil.saveFile(file); // 실제 파일 저장
                    if (savedFilename != null) {
                        FileDto fileDto = new FileDto();
                        fileDto.setTarget_type("ADMIN");
                        fileDto.setTarget_id(savedNoticeId);
                        fileDto.setFile_name(file.getOriginalFilename()); // 원본 파일명
                        fileDto.setFile_path(savedFilename); // 서버에 저장된 파일명
                        fileDto.setFile_uploader("관리자");

                        fileIDao.insertFile(fileDto);
                    }
                } catch (IOException e) {
                    System.err.println("첨부파일 저장 중 오류 발생: " + file.getOriginalFilename());
                    e.printStackTrace();
                    throw new RuntimeException("첨부파일 저장 중 오류 발생", e);
                }
            }
        }
        model.addAttribute("writeResult", result);
        System.out.println("공지사항 및 첨부파일 DB 저장 성공!");
    }
}
