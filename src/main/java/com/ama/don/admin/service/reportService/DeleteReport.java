package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class DeleteReport {

    private final ManageReportsIDao manageReportsIDao;

    public DeleteReport(ManageReportsIDao manageReportsIDao) {
        this.manageReportsIDao = manageReportsIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        Long reportId = (Long) map.get("reportId");
        String report_id = String.valueOf(reportId);
        System.out.println("\n >>> reportId : " + reportId);
        boolean result = false;

        int deleteReportResult = manageReportsIDao.deleteReport(report_id);
        if (deleteReportResult>0) {
            result = true;
        } else {
            System.err.println("[ERROR] No report ID like " + reportId);
        }
        model.addAttribute("deleteResult", result);
    }

}
