package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class NoticeDelete implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;

    public NoticeDelete(NoticesIDao noticesIDao) {
        this.noticesIDao = noticesIDao;
    }

    @Override
    public void execute(Model model) {
        boolean result = false;
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String noticeId = request.getParameter("notices_id");

        result = noticesIDao.deleteNotice(noticeId) > 0;

        model.addAttribute("deleteResult", result);
    }
}
