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

    public boolean execute(Model model) {
        Map<String, Object> map = model.asMap();
        Long reportId = (Long) map.get("reportId");
        String reportIdString = String.valueOf(reportId);
        boolean result = false;

        int deleteReportResult = manageReportsIDao.deleteReport(reportIdString);
        if (deleteReportResult>0) {
            result = true;
        } else {
            System.err.println("[ERROR] No report ID like " + reportId);
        }
        return result;
    }

}
