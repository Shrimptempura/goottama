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
        String reportId = (String) map.get("reportId");
        boolean result = false;

        int deleteReportResult = manageReportsIDao.deleteReport(reportId);
        if (deleteReportResult>0) {
            result = true;
        }
        model.addAttribute("deleteResult", result);
    }

}
