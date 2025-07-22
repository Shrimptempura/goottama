package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticesDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class NoticeModify implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;

    public NoticeModify(NoticesIDao noticesIDao) {
        this.noticesIDao = noticesIDao;
    }

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        NoticesDto modifiedNotice = (NoticesDto) map.get("modifiedNotice");
        boolean result = false;

        int noticesId = modifiedNotice.getNotices_id();
        result = noticesIDao.modifyNotice(modifiedNotice);
        if (result) {
            System.out.println("공지사항 DB 수정 성공! ID: " + noticesId);
        } else {
            System.err.println("공지사항 DB 수정 실패! ID: " + noticesId);
        }
        model.addAttribute("modifyResult", result);
    }
}
