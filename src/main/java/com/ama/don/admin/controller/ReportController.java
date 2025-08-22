package com.ama.don.admin.controller;

import com.ama.don.admin.dto.reportDTO.ReportSearchDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.reportService.*;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {

    private final GetReportListService getReportListService;
    private final GetReportDetail getReportDetail;
    private final SubmitReport submitReport;
    private final DeleteReport deleteReport;
    private final ChangeReportStatus changeReportStatus;

    public ReportController(ChangeReportStatus changeReportStatus, DeleteReport deleteReport, SubmitReport submitReport, GetReportListService getReportListService, GetReportDetail getReportDetail) {
        this.getReportListService = getReportListService;
        this.getReportDetail = getReportDetail;
        this.submitReport = submitReport;
        this.deleteReport = deleteReport;
        this.changeReportStatus = changeReportStatus;
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

    @GetMapping("/admin/reports/handle_report")
    public String handleReport(HttpServletRequest request, Model model,
                               @RequestParam("targetType") String targetType,
                               @RequestParam("targetId") Long targetId,
                               @RequestParam("reportId") String reportId) {

        model.addAttribute("targetType", targetType);
        model.addAttribute("targetId", targetId);
        model.addAttribute("reportId", reportId);

        if (targetType.equals("MEMBER")) {
            return "admin/reports/handle_member_report";
        } else if (targetType.equals("NOTICE") ||
                targetType.equals("POST") ||
                targetType.equals("COMMENT")) {
            return "admin/reports/handle_text_report";
        } else {
            request.setAttribute("errorTitle", "유효하지 않은 요청 파라미터");
            request.setAttribute("errorMessage", "처리할 수 없는 'targetType' 값입니다. 입력값을 확인해 주세요.");
            request.setAttribute("requestURI", request.getRequestURI());
            request.setAttribute("targetType", targetType);
            request.setAttribute("targetId", targetId);
            return "admin/adminErrorPage";
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";
        if (authentication != null && authentication.getPrincipal() instanceof ManageUserByAdmin) {
            ManageUserByAdmin userDetails = (ManageUserByAdmin) authentication.getPrincipal();
            UserTotalDataDTO userTotalData = userDetails.getUserTotalDataDTO();
            userId = String.valueOf(userTotalData.getUser_id());
        }
        model.addAttribute("userId", userId);
        model.addAttribute("request", request);
        submitReport.execite(model);

        // 결과 토스트를 위한 처리
        boolean isSuccess = (boolean) model.getAttribute("submitReportResult");
        model.addAttribute("result", isSuccess ? "report_success" : "report_failure");
        return "admin/reports/close_window";
    }

    @GetMapping("/admin/reports/delete_report")
    public String deleteReport(Model model, @RequestParam("reportId") Long reportId) {
        model.addAttribute("reportId", reportId);
        boolean isSuccess = deleteReport.execute(model);
        String resultMessage = isSuccess ? "report_delete_success" : "report_delete_failure";
        return "redirect:/admin/admin_index?menu=reports&result=" + resultMessage;
    }

    @PostMapping("/admin/reports/change_report_status")
    public String changeReportStatus(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        boolean isSuccess = changeReportStatus.execute(model);
        model.addAttribute("result", isSuccess ? "report_success" : "report_failure");
        return "admin/reports/close_window";
    }
}
