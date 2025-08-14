package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.MakeSanctionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional; // Import the Transactional annotation

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class CreateSanction {

    private final SanctionsIDao sanctionsIDao;
    private final ManageUserIDao manageUserIDao;

    public CreateSanction(SanctionsIDao sanctionsIDao, ManageUserIDao manageUserIDao) {
        this.sanctionsIDao = sanctionsIDao;
        this.manageUserIDao = manageUserIDao;
    }

    @Transactional
    public boolean execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String userId = request.getParameter("user_id");
        String sanctionType = request.getParameter("sanctions_types");
        String sanctionReason = request.getParameter("sanctions_reason");
        String adminId = request.getParameter("admin_account");
        String sanctions_start_date_str = request.getParameter("sanctions_start_date");
        String sanctions_end_date_str = request.getParameter("sanctions_end_date");

        Timestamp sanction_start = strToTimestamp(sanctions_start_date_str);
        Timestamp sanction_end = strToTimestamp(sanctions_end_date_str);

        MakeSanctionDTO makeSanctionDTO = new MakeSanctionDTO(userId, sanctionType, sanction_start, sanction_end, sanctionReason, adminId);

        try {
            sanctionsIDao.makeSanction(makeSanctionDTO);
            manageUserIDao.updateUserSanctionsAndStatus(userId, sanction_end, "suspended");
            return true;
        } catch (Exception e) {
            System.err.println("Transaction failed for user " + userId + ": " + e.getMessage());
            return false;
        }
    }

    public Timestamp strToTimestamp(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime strDateToLocalDateTime = LocalDateTime.parse(strDate, formatter);
        return Timestamp.valueOf(strDateToLocalDateTime);
    }
}