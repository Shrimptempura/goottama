package com.ama.don.admin.controller;

import com.ama.don.admin.dto.reportDTO.ReportSearchDTO;
import com.ama.don.admin.service.reportService.GetReportDetail;
import com.ama.don.admin.service.reportService.GetReportListService;
import com.ama.don.admin.service.reportService.SubmitReport;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {

    private final GetReportListService getReportListService;
    private final GetReportDetail getReportDetail;
    private final SubmitReport submitReport;

    public ReportController(SubmitReport submitReport, GetReportListService getReportListService, GetReportDetail getReportDetail) {
        this.getReportListService = getReportListService;
        this.getReportDetail = getReportDetail;
        this.submitReport = submitReport;
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

    @GetMapping("/admin/reports/report_data_modal")
    public String reportDataModal(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        getReportDetail.execute(model);
        return "admin/reports/report_data_modal";
    }

    @GetMapping("/admin/reports/reportForm")
    public String reportForm(Model model,
                             @RequestParam("targetType") String targetType,
                             @RequestParam("targetId") Long targetId) {

        model.addAttribute("targetType", targetType);
        model.addAttribute("targetId", targetId);
        return "admin/reports/reportForm";
    }

    @PostMapping("/admin/reports/submit_report")
    public String submitReport(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        submitReport.execite(model);

        // 결과 토스트를 위한 처리
        boolean isSuccess = (boolean) model.getAttribute("submitReportResult");
        model.addAttribute("result", isSuccess ? "report_success" : "report_failure");
        return "admin/reports/close_window";
    }

    @GetMapping("/admin/reports/delete_report")
    public String deleteReport(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        //execute
        return "redirect:admin/admin_index?menu=reports";
    }

    @GetMapping("/admin/reports/change_report_status")
    public String changeReportStatus(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        //execute
        return "redirect:admin/admin_index?menu=reports";
    }
}
