package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.List;
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

        int totalCount;
        List<NoticesDto> list;

        // 공지 검색 조건이 하나도 없으면 전체 공지 불러오기
        boolean isEmptySearch = (noticeSearchVO.getNoticeTitle() == null || noticeSearchVO.getNoticeTitle().isEmpty())
                && (noticeSearchVO.getNoticeContent() == null || noticeSearchVO.getNoticeContent().isEmpty())
                && noticeSearchVO.getNoticeDateStart() == null
                && noticeSearchVO.getNoticeDateEnd() == null;

        if (isEmptySearch) {
            totalCount = noticesIDao.countAllNotices();
            searchVO.pageCalculate(totalCount);
            System.out.println("totalCount : " + totalCount);
            // 페이징 계산에 맞춰 공지 리스트 가져오기
            list = noticesIDao.getAllNoticesPaginated(searchVO.getRowStart() - 1, searchVO.getDisplayRowCount());
        } else {
            totalCount = noticesIDao.countSearchNotice(noticeSearchVO, searchVO);
            searchVO.pageCalculate(totalCount);
            list = noticesIDao.searchNotice(noticeSearchVO, searchVO);
        }

        model.addAttribute("notices", list);
        model.addAttribute("searchVO", searchVO);
    }
}
