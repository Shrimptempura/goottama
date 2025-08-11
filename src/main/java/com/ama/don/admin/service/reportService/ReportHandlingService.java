package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dto.sanctionsDTO.MakeSanctionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 신고 상태 변경 및 유저 재제 내역 객체 생성
 */
@Service
public class ReportHandlingService {

    private final ManageReportsIDao manageReportsIDao;

    public ReportHandlingService(ManageReportsIDao manageReportsIDao) {
        this.manageReportsIDao = manageReportsIDao;
    }

    @Transactional
    public boolean handleReportStatus(String reportId, String statusChanged) {
        int handleReportStatusResult = manageReportsIDao.handleReportStatus(reportId, statusChanged);
        if (handleReportStatusResult == 0) {
            throw new RuntimeException("신고 상태 변경 실패");
        }
        return true;
    }

    public MakeSanctionDTO createMemberSanctionDTO(HttpServletRequest request, String targetId, String sanctionReason, String adminId) {
        String startDateString = request.getParameter("startDate");
        String endDateString = request.getParameter("endDate");
        String sanctionsType = request.getParameter("sanctionsType");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        Timestamp startDateTimeStamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endDateTimeStamp = Timestamp.valueOf(endDate.atStartOfDay());

        return new MakeSanctionDTO(targetId, sanctionsType, startDateTimeStamp, endDateTimeStamp, sanctionReason, adminId);
    }

}
