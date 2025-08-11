package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dto.reportDTO.ReportDTO;
import com.ama.don.admin.dto.reportDTO.SubmitReportForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class SubmitReport {

    private final ManageReportsIDao manageReportsIDao;

    public SubmitReport(ManageReportsIDao manageReportsIDao) {
        this.manageReportsIDao = manageReportsIDao;
    }

    public void execite(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String reporterId = (String) map.get("userId");
        String targetId = request.getParameter("targetId");
        String targetType = request.getParameter("targetType");
        String reportContent = request.getParameter("reportContent");
        SubmitReportForm submitReportForm = new SubmitReportForm();
        submitReportForm.setUserId(reporterId);
        submitReportForm.setReportContent(reportContent);
        submitReportForm.setTargetId(targetId);
        submitReportForm.setTargetType(targetType);

        int submitReport = manageReportsIDao.submitReport(submitReportForm);
        if (submitReport >= 1) {
            model.addAttribute("submitReportResult", true);
        } else {
            model.addAttribute("submitReportResult", false);
        }
    }
}
