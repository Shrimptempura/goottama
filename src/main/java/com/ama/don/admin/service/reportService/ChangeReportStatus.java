package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.MakeSanctionDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class ChangeReportStatus {

    private final ManageReportsIDao manageReportsIDao;
    private final SanctionsIDao sanctionsIDao;

    public ChangeReportStatus(ManageReportsIDao manageReportsIDao, SanctionsIDao sanctionsIDao) {
        this.manageReportsIDao = manageReportsIDao;
        this.sanctionsIDao = sanctionsIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String targetType = request.getParameter("targetType");
        String reportId = request.getParameter("reportId");
        String statusChanged = request.getParameter("status");
        String targetId = request.getParameter("targetId");
        String sanctionReason = request.getParameter("sanctionReason");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminId = "";
        if (authentication != null && authentication.getPrincipal() instanceof ManageUserByAdmin) {
            ManageUserByAdmin userDetails = (ManageUserByAdmin) authentication.getPrincipal();
            UserTotalDataDTO userTotalData = userDetails.getUserTotalDataDTO();
            adminId = String.valueOf(userTotalData.getUser_id());
        }

        if (targetType.equals("MEMBER")) {
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            LocalDate endDate = LocalDate.parse(endDateString, formatter);
            Timestamp startDateTimeStamp = Timestamp.valueOf(startDate.atStartOfDay());
            Timestamp endDateTimeStamp = Timestamp.valueOf(endDate.atStartOfDay());
            String sanctionsType = request.getParameter("sanctionsType");
            MakeSanctionDTO makeSanctionDTO = new MakeSanctionDTO(targetId, sanctionsType, startDateTimeStamp, endDateTimeStamp, sanctionReason, adminId);
            sanctionsIDao.makeSanction(makeSanctionDTO);
        } else {
            String visibility = request.getParameter("visibility");

        }



        //여기서 신고아이디를 토대로 유저와 글 모두 처리 해야 함. 
        // 차라리 유저 처리 메서드와 글 처리 메서드를 만들어서 호출 하느게 낫겠음
        boolean changeReportStatusResult = false;



//        int changeStatusResult = manageReportsIDao.handleReportStatus(reportId, statusChanged);
//        if (changeStatusResult > 0) {
//            changeReportStatusResult = true;
//        }
//        model.addAttribute("changeReportStatusResult", changeReportStatusResult);
    }
}
