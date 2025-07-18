package com.ama.don.admin.controller;

import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.service.noticeService.GetNoticeListService;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class AdminNoticeController {

    @Autowired
    private GetNoticeListService getNoticeListService;

    @PostMapping("/admin/notices/notice_list")
    public String noticeList(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute NoticeSearchVO noticeSearchVO,
                             @RequestParam(required = false) String noticeDateStart,
                             @RequestParam(required = false) String noticeDateEnd) {

        if (noticeDateStart != null && !noticeDateStart.isEmpty()) {
            noticeSearchVO.setNoticeDateStart(Timestamp.valueOf(noticeDateStart + " 00:00:00"));
        }
        if (noticeDateEnd != null && !noticeDateEnd.isEmpty()) {
            noticeSearchVO.setNoticeDateEnd(Timestamp.valueOf(noticeDateEnd + " 23:59:59"));
        }
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("noticeSearchVO", noticeSearchVO);
        getNoticeListService.execute(model);

        return "admin/notices/notice_list";
    }

    @GetMapping("admin/notices/notice_page")
    public String noticePage(Model model){
        return "admin/notices/notice_page";
    }
}
