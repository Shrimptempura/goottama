package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.noticeDTO.NoticesDto;
import com.ama.don.admin.temp.FileIDao;
import com.ama.don.common.enums.TargetType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * 특정 공지사항의 상세 내용을 조회하는 비즈니스 로직을 수행하는 서비스 구현체.<br/>
 * 공지사항 본문 정보와 함께 연결된 첨부파일 목록을 함께 조회하여 반환함.
 */
@Service
public class GetNoticeDetail implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;
    private final FileIDao fileIDao;

    public GetNoticeDetail(NoticesIDao noticesIDao, FileIDao fileIDao){
        this.noticesIDao = noticesIDao;
        this.fileIDao = fileIDao;
    }

    /**
     * 특정 공지사항의 상세 정보를 조회하는 작업 실행함.<br/>
     * 요청에서 공지 ID를 받아 해당 공지사항의 본문 내용과<br/>
     * 연결된 모든 첨부파일 목록을 데이터베이스에서 조회하여 모델에 추가함.<br/>
     * **만약 해당 ID의 공지사항을 찾을 수 없으면 RuntimeException 발생시킴.**
     *
     * @param model Spring UI Model. 요청 정보(HttpServletRequest)를 받아오고,<br/>
     * 조회된 공지사항 정보({@link NoticesDto})와<br/>
     * 첨부파일 목록({@link java.util.List}<{@link com.ama.don.common.dto.FileDto}>)을 모델에 추가하는 데 사용됨.
     * @throws RuntimeException 조회할 공지사항을 찾을 수 없는 경우 발생함.
     */
    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        String noticeId = (String) model.getAttribute("noticesId");
        // String noticeId = request.getParameter("notices_id");
        NoticesDto notice = noticesIDao.getNoticeById(noticeId);
        System.out.println("\nnoticeId : "+noticeId);
        // 공지사항을 찾을 수 없는 경우 예외 발생시킴.
        if (notice == null) {
            throw new RuntimeException("공지사항을 찾을 수 없음. ID: " + noticeId);
        }

        Long noticeIdLong = Long.parseLong(noticeId);
        notice.setAttachedFiles(fileIDao.getFilesByTargetAndUploader(TargetType.ADMIN, noticeIdLong, "관리자"));

        model.addAttribute("notice", notice);
    }
}
