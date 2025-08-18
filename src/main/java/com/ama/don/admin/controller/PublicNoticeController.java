package com.ama.don.admin.controller;

import com.ama.don.admin.dto.noticeDTO.NoticeSearchDTO;
import com.ama.don.admin.service.noticeService.GetNoticeDetail;
import com.ama.don.admin.service.noticeService.GetNoticeListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublicNoticeController {

    private final GetNoticeListService getNoticeListService;
    private final GetNoticeDetail getNoticeDetail;

    public PublicNoticeController(GetNoticeListService getNoticeListService,
                                  GetNoticeDetail getNoticeDetail) {
        this.getNoticeDetail = getNoticeDetail;
        this.getNoticeListService = getNoticeListService;
    }

    @GetMapping("/notice/notice_list")
    public String noticeList(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute NoticeSearchDTO noticeSearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("noticeSearchDTO", noticeSearchDTO);
        getNoticeListService.execute(model);

        return "common/notice/notice_list";
    }

    @RequestMapping("/notice/notice_detail")
    public String noticeDetail(Model model, @RequestParam("notices_id") String noticesId){
        model.addAttribute("noticesId", noticesId);
        getNoticeDetail.execute(model);
        return "common/notice/notice_detail";
    }
}
