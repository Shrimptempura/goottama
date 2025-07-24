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

/**
 * 새로운 공지사항 작성 비즈니스 로직을 수행하는 서비스 구현체.<br/>
 * 클라이언트로부터 받은 공지사항 내용과 첨부된 파일들을 데이터베이스에 저장함.<br/>
 * TUI 에디터로 삽입된 이미지는 에디터 측에서 별도로 처리되므로,
 * 여기서는 일반 첨부파일만 관리됨.<br/>
 * 모든 저장 작업은 단일 트랜잭션으로 처리되어 데이터 일관성 보장함.
 */
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

    /**
     * 새로운 공지사항 작성 작업을 실행함.<br/>
     * 모델에서 공지사항 정보({@link com.ama.don.admin.dto.NoticesDto})와<br/>
     * 첨부된 파일 데이터({@link org.springframework.web.multipart.MultipartHttpServletRequest})를 받아 처리함.<br/>
     * 먼저 공지사항 본문을 DB에 저장하고, 이어서 첨부파일들을 물리적으로 저장하고 DB에 파일 정보 기록함.<br/>
     *
     * @param model Spring UI Model. 새로운 공지 정보, 파일 데이터를 포함하며,<br/>
     * 작성 결과(`writeResult`)를 모델에 추가하는 데 사용됨.
     * @throws RuntimeException 공지사항 본문 저장 또는 첨부파일 저장/처리 중 오류 발생 시 발생함.
     */
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
