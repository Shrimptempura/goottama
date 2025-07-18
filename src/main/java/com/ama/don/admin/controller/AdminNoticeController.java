package com.ama.don.admin.controller;

import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.service.noticeService.GetNoticeListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminNoticeController {

    @Autowired
    private GetNoticeListService getNoticeListService;

    /**
     * 공지 검색을 위한 POST 요청 처리
     * @param model
     * @param searchVO
     * @param noticeSearchVO
     * @return
     */
    @PostMapping("/admin/notices/notice_list")
    public String noticeList(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute NoticeSearchVO noticeSearchVO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("noticeSearchVO", noticeSearchVO);
        getNoticeListService.execute(model);

        return "admin/notices/notice_list";
    }

    /**
     * 처음 공지 페이지 접속 시 전체 공지 로드를 위한 GET 요청 처리
     * @param model
     * @param searchVO
     * @param noticeSearchVO
     * @return
     */
    @GetMapping("admin/notices/notice_page")
    public String noticePage(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute NoticeSearchVO noticeSearchVO){
        // 초기화
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (noticeSearchVO == null) {
            noticeSearchVO = new NoticeSearchVO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("noticeSearchVO", noticeSearchVO);

        getNoticeListService.execute(model);
        return "admin/notices/notice_page";
    }
}
