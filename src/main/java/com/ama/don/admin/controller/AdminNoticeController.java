package com.ama.don.admin.controller;

import com.ama.don.admin.service.noticeService.GetNoticeListService;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminNoticeController {

    @Autowired
    private GetNoticeListService getNoticeListService;

    @PostMapping("/admin/notices/notice_list")
    public String noticeList(HttpServletRequest request, Model model, SearchVO searchVO){
        model.addAttribute("request", request);
        model.addAttribute("searchVO", searchVO);
        getNoticeListService.execute(model);
        return "admin/notices/notice_list";
    }
}
