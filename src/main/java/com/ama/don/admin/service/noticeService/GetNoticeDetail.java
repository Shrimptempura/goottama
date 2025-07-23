package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.temp.FileIDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class GetNoticeDetail implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;
    private final FileIDao fileIDao;

    public GetNoticeDetail(NoticesIDao noticesIDao, FileIDao fileIDao){
        this.noticesIDao = noticesIDao;
        this.fileIDao = fileIDao;
    }

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String noticeId = request.getParameter("notices_id");
        NoticesDto notice = noticesIDao.getNoticeById(noticeId);

        Long noticeIdLong = Long.parseLong(noticeId);
        if (notice != null) {
            notice.setAttachedFiles(fileIDao.getFilesByTarget("ADMIN", noticeIdLong));
        }

        model.addAttribute("notice", notice);
    }
}
