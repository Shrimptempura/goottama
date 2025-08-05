package com.ama.don.admin.controller;

import com.ama.don.admin.dto.NoticeSearchDTO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.dto.ReportSearchDTO;
import com.ama.don.admin.service.noticeService.*;
import com.ama.don.admin.service.reportService.GetReportListService;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Controller
public class ReportController {

    private final GetReportListService getReportListService;

    public ReportController(GetReportListService getReportListService) {
        this.getReportListService = getReportListService;
    }

    @PostMapping("/admin/reports/report_list")
    public String reportList(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute ReportSearchDTO reportSearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("reportSearchDTO", reportSearchDTO);
        getReportListService.execute(model);

        return "admin/reports/report_list";
    }

    @GetMapping("admin/reports/report_page")
    public String reportPage(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute ReportSearchDTO reportSearchDTO){
        // 초기화
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (reportSearchDTO == null) {
            reportSearchDTO = new ReportSearchDTO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("reportSearchDTO", reportSearchDTO);

        getReportListService.execute(model);
        return "admin/reports/report_page";
    }

//    @RequestMapping("/admin/notices/notice_detail")
//    public String noticeDetail(Model model, HttpServletRequest request){
//        model.addAttribute("request", request);
//        getNoticeDetail.execute(model);
//        return "admin/notices/notice_detail";
//    }
//
//    @PostMapping("/admin/notices/notice_write")
//    public String noticeWrite(Model model, MultipartHttpServletRequest mtfRequest,
//                              @RequestParam("title") String title,
//                              @RequestParam("content") String content,
//                              @RequestParam(value = "isPinned", defaultValue = "false") boolean isPinned){
//        model.addAttribute("mtfRequest", mtfRequest);
//
//        NoticesDto newNotice = new NoticesDto();
//        newNotice.setNotices_title(title);
//        newNotice.setNotices_content(content);
//        newNotice.setNotices_is_pinned(isPinned);
//        // newNotice.setNotices_file_path(null);
//        model.addAttribute("newNotice", newNotice);
//        writeNotice.execute(model);
//        Boolean result = (Boolean) model.asMap().get("writeResult");
//        String message = result ? "write_success" : "write_failure";
//        System.out.println(">>> "+ message);
//        return "redirect:notice_page";
//    }
//
//    @RequestMapping("/admin/notices/notice_modify_view")
//    public String noticeModifyView(Model model, HttpServletRequest request){
//        model.addAttribute("request", request);
//        getNoticeDetail.execute(model);
//        return "admin/notices/notice_modify_view";
//    }
//
//    @RequestMapping("/admin/notices/notice_modify")
//    public String noticeModify(Model model,
//                               MultipartHttpServletRequest mtfRequest,
//                               @RequestParam("notices_id") int noticesId,
//                               @RequestParam("title") String title,
//                               @RequestParam("content") String content,
//                               @RequestParam(value = "isPinned", defaultValue = "false") boolean isPinned,
//                               @RequestParam(value = "deleteFileIds", required = false) List<Long> deleteFileIds){
//        NoticesDto modifiedNotice = new NoticesDto();
//        modifiedNotice.setNotices_id(noticesId);
//        modifiedNotice.setNotices_title(title);
//        modifiedNotice.setNotices_content(content);
//        modifiedNotice.setNotices_is_pinned(isPinned);
//
//        model.addAttribute("modifiedNotice", modifiedNotice);
//        model.addAttribute("deleteFileIds", deleteFileIds);
//        model.addAttribute("mtfRequest", mtfRequest);
//        noticeModify.execute(model);
//        Boolean result = (Boolean) model.asMap().get("modifyResult");
//        String message = result ? "modify_success" : "modify_failure";
//        System.out.println(">>> "+ message);
//        return "redirect:notice_detail?notices_id="+noticesId;
//    }
//
//    @RequestMapping("/admin/notices/notice_delete")
//    public String noticeDelete(Model model, HttpServletRequest request){
//        model.addAttribute("request", request);
//        noticeDelete.execute(model);
//        Boolean result = (Boolean) model.asMap().get("deleteResult");
//        String message = result ? "delete_success" : "delete_failure";
//        System.out.println(">>> "+message);
//        return "redirect:notice_page";
//    }
}
