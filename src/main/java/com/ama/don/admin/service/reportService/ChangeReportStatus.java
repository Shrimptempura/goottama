package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class ChangeReportStatus {

    private final ManageReportsIDao manageReportsIDao;

    public ChangeReportStatus(ManageReportsIDao manageReportsIDao) {
        this.manageReportsIDao = manageReportsIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        String reportId = (String) map.get("reportId");
        String statusChanged = (String) map.get("status");
        boolean changeReportStatusResult = false;

        int changeStatusResult = manageReportsIDao.handleReportStatus(reportId, statusChanged);
        if (changeStatusResult > 0) {
            changeReportStatusResult = true;
        }
        model.addAttribute("changeReportStatusResult", changeReportStatusResult);
    }
}
