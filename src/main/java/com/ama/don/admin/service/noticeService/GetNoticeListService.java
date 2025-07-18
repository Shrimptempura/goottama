package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetNoticeListService implements NoticeServiceInterface{

    @Autowired
    private NoticesIDao noticesIDao;

    public void allNotices(Model model){
        System.out.println(22222);
        Map<String, Object> map = model.asMap();
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<NoticesDto> list = noticesIDao.getAllNotices();
        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
    }

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        NoticeSearchVO noticeSearchVO = (NoticeSearchVO) map.get("noticeSearchVO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<NoticesDto> dtoList;
        int total = 0;

        // 검색 조건이 없거나 비어있으므로 전체 공지사항을 가져옴
        if (noticeSearchVO == null ||
                (noticeSearchVO.getNoticeTitle() == null || noticeSearchVO.getNoticeTitle().isEmpty()) &&
                (noticeSearchVO.getNoticeContent() == null || noticeSearchVO.getNoticeContent().isEmpty()) &&
                (noticeSearchVO.getNoticeDateStart() == null || noticeSearchVO.getNoticeDateStart().isEmpty()) &&
                (noticeSearchVO.getNoticeDateEnd() == null || noticeSearchVO.getNoticeDateEnd().isEmpty())) {
            dtoList = noticesIDao.getAllNotices();

            total = noticesIDao.countAllNotices();
        // 검색 조건이 있으면 검색 된 공지사항을 가져옴
        } else {
            System.out.println("제목: " + noticeSearchVO.getNoticeTitle());
            System.out.println("내용: " + noticeSearchVO.getNoticeContent());
            System.out.println("시작일: " + noticeSearchVO.getNoticeDateStart());
            System.out.println("종료일: " + noticeSearchVO.getNoticeDateEnd());
            total = noticesIDao.countSearchNotice(noticeSearchVO);
            dtoList = noticesIDao.searchNotice(noticeSearchVO);
        }
        for (NoticesDto dto : dtoList) {
            Map<String, Object> row = new HashMap<>();
            row.put("noticesId", dto.getNotices_id());
            row.put("noticesTitle", dto.getNotices_title());
            row.put("noticesIsPinned", dto.isNotices_is_pinned());
            row.put("noticesCreatedAt", dto.getNotices_created_at());
            row.put("noticesFilePath", dto.getNotices_file_path());
            row.put("noticesContent", dto.getNotices_content());
            mapList.add(row);
        }
        // Pagination
        searchVO.pageCalculate(total);

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);
    }
}
