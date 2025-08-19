package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class ChangeUserRoleService {

    private final ManageUserIDao manageUserIDao;

    public ChangeUserRoleService(ManageUserIDao manageUserIDao) {
        this.manageUserIDao = manageUserIDao;
    }

    public boolean execute(Model model) {
        Map<String, Object> map = model.asMap();
        String userIdStr = (String) map.get("userId");
        String userRoleStr = (String) map.get("userRole");
        Long userId = Long.parseLong(userIdStr);
        Long userRole = Long.parseLong(userRoleStr);
        int update = 0;

        if (userId != null && userRole != null) {
            try {
                update = manageUserIDao.changeUserRole(userId, userRole);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.err.println("[ERROR] Invalid role provided: " + userRole);
            return false;
        }

        if (update > 0) {
            return true;
        } else {
            System.err.println("[ERROR] Fail to update role for user id: " + userId);
            return false;
        }
    }
}
