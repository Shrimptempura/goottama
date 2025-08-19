package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dto.sanctionsDTO.MakeSanctionDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.sanctionService.SanctionFromReport;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * 신고 처리 메서드
 */
@Service
public class ChangeReportStatus {

    private final ReportHandlingService reportHandlingService;
    private final SanctionFromReport sanctionFromReport;

    public ChangeReportStatus(SanctionFromReport sanctionFromReport, ReportHandlingService reportHandlingService) {
        this.sanctionFromReport = sanctionFromReport;
        this.reportHandlingService = reportHandlingService;
    }

    public boolean execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");

        String reportId = request.getParameter("reportId");
        String statusChanged = request.getParameter("status");
        String targetType = request.getParameter("targetType");
        String targetId = request.getParameter("targetId");
        String sanctionReason = request.getParameter("sanctionReason");
        String adminId = getAdminId(); // 관리자 ID를 가져오는 메서드
        boolean result;

        // 관리자 ID가 없으면 false 반환
        if (adminId == null) {
            return false;
        }
        if ("MEMBER".equals(targetType)) {
            MakeSanctionDTO makeSanctionDTO = reportHandlingService.createMemberSanctionDTO(request, targetId, sanctionReason, adminId);
            result = sanctionFromReport.makeSanctionAndHandleReport(makeSanctionDTO, reportId, statusChanged);
        } else {
            result = reportHandlingService.handleReportStatus(reportId, statusChanged);
        }
        return result;
    }

    // ReportController의 submitReport 참조
    private String getAdminId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof ManageUserByAdmin) {
            ManageUserByAdmin userDetails = (ManageUserByAdmin) authentication.getPrincipal();
            UserTotalDataDTO userTotalData = userDetails.getUserTotalDataDTO();
            Long roles_id = userTotalData.getRoles_id();
            if (roles_id != null && roles_id >= 300) { // 관리자 이상
                return String.valueOf(userTotalData.getUser_id());
            }
        }
        return null;
    }
}
