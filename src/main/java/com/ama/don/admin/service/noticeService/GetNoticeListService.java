package com.ama.don.admin.service.noticeService;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 공지사항 목록을 조회하는 비즈니스 로직을 수행하는 서비스 구현체.<br/>
 * 검색 조건 및 페이지네이션 정보에 따라 전체 공지 또는 검색된 공지 목록을 반환함.<br/>
 * 조회된 공지사항 데이터를 가공하여 뷰에 전달할 준비를 함.
 */
@Service
public class GetNoticeListService implements NoticeServiceInterface{

    private final NoticesIDao noticesIDao;
    private final NoticeFileService noticeFileService;

    public GetNoticeListService(NoticesIDao noticesIDao, NoticeFileService noticeFileService) {
        this.noticesIDao = noticesIDao;
        this.noticeFileService = noticeFileService;
    }

    /**
     * 공지사항 목록 조회 작업을 실행함.<br/>
     * 모델에서 검색 조건(`NoticeSearchVO`)과 페이지네이션 정보(`SearchVO`)를 추출함.<br/>
     * 검색 조건 유무에 따라 전체 공지 또는 검색된 공지를 DB에서 조회하고,<br/>
     * 페이지 계산을 수행한 뒤, 조회된 데이터를 맵 형태로 가공하여 모델에 추가함.
     *
     * @param model Spring UI Model. 검색 및 페이지네이션 정보를 포함하며,<br/>
     * 조회된 공지사항 목록(`list`)과 업데이트된 페이지네이션 정보(`searchVO`)를 모델에 추가하는 데 사용됨.
     */
    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        NoticeSearchVO noticeSearchVO = (NoticeSearchVO) map.get("noticeSearchVO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<NoticesDto> dtoList;
        int total;

        // 검색 조건이 없거나 비어있으므로 전체 공지사항을 가져옴
        if (noticeSearchVO == null ||
                (noticeSearchVO.getNoticeTitle() == null || noticeSearchVO.getNoticeTitle().isEmpty()) &&
                (noticeSearchVO.getNoticeContent() == null || noticeSearchVO.getNoticeContent().isEmpty()) &&
                (noticeSearchVO.getNoticeDateStart() == null || noticeSearchVO.getNoticeDateStart().isEmpty()) &&
                (noticeSearchVO.getNoticeDateEnd() == null || noticeSearchVO.getNoticeDateEnd().isEmpty())) {
            total = noticesIDao.countAllNotices();
            dtoList = noticesIDao.getAllNotices(searchVO);
        // 검색 조건이 있으면 검색 된 공지사항을 가져옴
        } else {
            System.out.println("제목: " + noticeSearchVO.getNoticeTitle());
            System.out.println("내용: " + noticeSearchVO.getNoticeContent());
            System.out.println("시작일: " + noticeSearchVO.getNoticeDateStart());
            System.out.println("종료일: " + noticeSearchVO.getNoticeDateEnd());
            total = noticesIDao.countSearchNotice(noticeSearchVO);
            dtoList = noticesIDao.searchNotice(noticeSearchVO, searchVO);
        }

        searchVO.pageCalculate(total);

        for (NoticesDto dto : dtoList) {
            Map<String, Object> row = new HashMap<>();
            row.put("noticesId", dto.getNotices_id());
            row.put("noticesTitle", dto.getNotices_title());
            row.put("noticesIsPinned", dto.isNotices_is_pinned());
            row.put("noticesCreatedAt", dto.getNotices_created_at());
            row.put("noticesContent", dto.getNotices_content());
            mapList.add(row);
        }

        noticeFileService.removeNegativeTargetIdFiles();

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);
    }
}
