package com.ama.don.admin.controller;

import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.service.noticeService.*;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class AdminNoticeController {

    private final GetNoticeListService getNoticeListService;
    private final GetNoticeDetail getNoticeDetail;
    private final WriteNotice writeNotice;
    private final NoticeModify noticeModify;
    private final  NoticeDelete noticeDelete;
    public AdminNoticeController(GetNoticeListService getNoticeListService, GetNoticeDetail getNoticeDetail, WriteNotice writeNotice,
                                 NoticeModify noticeModify, NoticeDelete noticeDelete) {
        this.getNoticeListService = getNoticeListService;
        this.getNoticeDetail = getNoticeDetail;
        this.writeNotice = writeNotice;
        this.noticeModify = noticeModify;
        this.noticeDelete = noticeDelete;
    }

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

    @RequestMapping("/admin/notices/notice_detail")
    public String noticeDetail(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        getNoticeDetail.execute(model);
        return "admin/notices/notice_detail";
    }

    @RequestMapping("/admin/notices/notice_write_view")
    public String writeView(){
        return "admin/notices/notice_write_view";
    }

    @PostMapping("/admin/notices/notice_write")
    public String noticeWrite(Model model, MultipartHttpServletRequest mtfRequest,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam(value = "isPinned", defaultValue = "false") boolean isPinned){
        model.addAttribute("mtfRequest", mtfRequest);

        NoticesDto newNotice = new NoticesDto();
        newNotice.setNotices_title(title);
        newNotice.setNotices_content(content);
        newNotice.setNotices_is_pinned(isPinned);
        // newNotice.setNotices_file_path(null);
        model.addAttribute("newNotice", newNotice);
        writeNotice.execute(model);
        Boolean result = (Boolean) model.asMap().get("writeResult");
        String message = result ? "write_success" : "write_failure";
        System.out.println(">>> "+ message);
        return "redirect:notice_page";
    }

    @RequestMapping("/admin/notices/notice_modify_view")
    public String noticeModifyView(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        getNoticeDetail.execute(model);
        return "admin/notices/notice_modify_view";
    }

    @RequestMapping("/admin/notices/notice_modify")
    public String noticeModify(Model model,
                               @RequestParam("notices_id") int noticesId,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam(value = "isPinned", defaultValue = "false") boolean isPinned){
        NoticesDto modifiedNotice = new NoticesDto();
        modifiedNotice.setNotices_id(noticesId);
        modifiedNotice.setNotices_title(title);
        modifiedNotice.setNotices_content(content);
        modifiedNotice.setNotices_is_pinned(isPinned);
        // modifiedNotice.setNotices_file_path(null);

        model.addAttribute("modifiedNotice", modifiedNotice);
        noticeModify.execute(model);
        Boolean result = (Boolean) model.asMap().get("modifyResult");
        String message = result ? "modify_success" : "modify_failure";
        System.out.println(">>> "+ message);
        return "redirect:notice_detail?notices_id="+noticesId;
    }

    @RequestMapping("/admin/notices/notice_delete")
    public String noticeDelete(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        noticeDelete.execute(model);
        Boolean result = (Boolean) model.asMap().get("deleteResult");
        String message = result ? "delete_success" : "delete_failure";
        System.out.println(">>> "+message);
        return "redirect:notice_page";
    }
}
