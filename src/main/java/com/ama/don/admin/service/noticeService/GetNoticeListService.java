package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class GetNoticeListService implements NoticeServiceInterface{

    @Autowired
    private NoticesIDao noticesIDao;

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        NoticeSearchVO noticeSearchVO = (NoticeSearchVO) map.get("noticeSearchVO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");

    }
}
