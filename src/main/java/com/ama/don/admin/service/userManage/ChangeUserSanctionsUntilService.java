package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class ChangeUserSanctionsUntilService {

    private final ManageUserIDao manageUserIDao;

    public ChangeUserSanctionsUntilService(ManageUserIDao manageUserIDao) {
        this.manageUserIDao = manageUserIDao;
    }

    @Transactional
    public boolean execute(Model model) {
        Map<String, Object> map = model.asMap();
        String userId = (String) map.get("userId");
        String userSanctionsUntilString = (String) map.get("userSanctionsUntil");
        String userStatus = (String) map.get("userStatus");
        int update = 0;
        System.out.println("\n>>> userId : " + userId);
        System.out.println(">>> userSanctionsUntilString : " + userSanctionsUntilString);
        System.out.println(">>> userStatus : " + userStatus + "\n");
        Timestamp userSanctionsUntil = null;

        if ("suspended".equals(userStatus)) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate userSanctionsUntilLocalDate = LocalDate.parse(userSanctionsUntilString, formatter);
                userSanctionsUntil = Timestamp.valueOf(userSanctionsUntilLocalDate.atStartOfDay());
                update = manageUserIDao.updateUserSanctionsAndStatus(userId, userSanctionsUntil, userStatus);
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to parse date string: " + userSanctionsUntilString);
                return false;
            }
        } else if ("active".equals(userStatus) || "deleted".equals(userStatus)) {
            update = manageUserIDao.updateUserSanctionsAndStatus(userId, null, userStatus);
        } else {
            System.err.println("[ERROR] Invalid user status provided: " + userStatus);
            return false;
        }

        if (update > 0) {
            return true;
        } else {
            System.err.println("[ERROR] Fail to update user status and sanctions until for user id: " + userId);
            return false;
        }
    }
}