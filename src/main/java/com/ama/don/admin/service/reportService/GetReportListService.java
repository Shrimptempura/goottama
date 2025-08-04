package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dto.ReportDTO;
import com.ama.don.admin.dto.ReportSearchDTO;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetReportListService {

    private final ManageReportsIDao manageReportsIDao;

    public GetReportListService(ManageReportsIDao manageReportsIDao) {
        this.manageReportsIDao = manageReportsIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        ReportSearchDTO reportSearchDTO = (ReportSearchDTO) map.get("reportSearchDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<ReportDTO> dtoList;
        int total;
    }
}
