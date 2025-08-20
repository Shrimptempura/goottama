package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.MakeSanctionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDate;
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

    @Transactional
    public boolean createSanctionFromChange(String userId, String sanctionsUntilStr, String sanctionType, String sanctionReason, String adminId) {
        Timestamp sanction_start = Timestamp.valueOf(LocalDateTime.now());
        Timestamp sanction_end = strToTimestamp(sanctionsUntilStr);
        String userStatus = "suspended";

        MakeSanctionDTO makeSanctionDTO = new MakeSanctionDTO(userId, sanctionType, sanction_start, sanction_end, sanctionReason, adminId);

        try {
            sanctionsIDao.makeSanction(makeSanctionDTO);
            manageUserIDao.updateUserSanctionsAndStatus(userId, sanction_end, userStatus);
            return true;
        } catch (Exception e) {
            System.err.println("Transaction failed for user " + userId + " (sanction change): " + e.getMessage());
            return false;
        }
    }

    public Timestamp strToTimestamp(String strDate) {
        // 입력 문자열의 길이를 확인하여 포맷을 동적으로 선택
        //  현재 선택의 방식이 일관적이지 않아서 생기는 문제
        if (strDate.length() > 10) {
            // 'yyyy-MM-dd'T'HH:mm' 형식
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime strDateToLocalDateTime = LocalDateTime.parse(strDate, formatter);
            return Timestamp.valueOf(strDateToLocalDateTime);
        } else {
            // 'yyyy-MM-dd' 형식 (날짜만 있는 경우)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate strDateToLocalDate = LocalDate.parse(strDate, formatter);
            // 자정(00:00:00)을 기준으로 Timestamp를 생성.
            return Timestamp.valueOf(strDateToLocalDate.atStartOfDay());
        }
    }
}