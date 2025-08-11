package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dto.reportDTO.ReportDTO;
import com.ama.don.admin.dto.reportDTO.ReportSearchDTO;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
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

        if (reportSearchDTO == null ||
                (reportSearchDTO.getUserId() == null) &&
                        (reportSearchDTO.getReportContent() == null || reportSearchDTO.getReportContent().isEmpty()) &&
                        (reportSearchDTO.getTargetType() == null || reportSearchDTO.getTargetType().isEmpty()) &&
                        (reportSearchDTO.getTargetId() == null) &&
                        (reportSearchDTO.getReportStatus() == null || reportSearchDTO.getReportStatus().isEmpty()) &&
                        (reportSearchDTO.getReportDateStart() == null) &&
                        (reportSearchDTO.getReportDateEnd() == null)) {
            total = manageReportsIDao.countAllReports();
            dtoList = manageReportsIDao.getAllReports(searchVO);

        } else {
            total = manageReportsIDao.countSearchReports(reportSearchDTO);
            dtoList = manageReportsIDao.searchReports(searchVO, reportSearchDTO);
        }

        searchVO.pageCalculate(total);

        for (ReportDTO dto : dtoList) {
            Map<String, Object> row = new HashMap<>();
            row.put("reportId", dto.getReportId());
            row.put("userId", dto.getUserId());
            row.put("reportDate", dto.getReportDate());
            row.put("reportContent", dto.getReportContent());
            row.put("targetType", dto.getTargetType());
            row.put("targetId", dto.getTargetId());
            row.put("reportStatus", dto.getReportStatus());
            mapList.add(row);
        }

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);

    }
}
