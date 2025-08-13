package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class CreateSanction {

    private final SanctionsIDao sanctionsIDao;
    private final ManageUserIDao manageUserIDao;

    public CreateSanction(SanctionsIDao sanctionsIDao, ManageUserIDao manageUserIDao) {
        this.sanctionsIDao = sanctionsIDao;
        this.manageUserIDao = manageUserIDao;
    }

    public boolean execute(Model model) {
        boolean result = false;
        Map<String, Object> map=model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        String userId = request.getParameter("user_id");
        String sanctionType = request.getParameter("sanctions_types");
        String sanctionReason = request.getParameter("sanctions_reason");
        String adminId = request.getParameter("admin_account");
        String sanctions_start_date_str = request.getParameter("sanctions_start_date");
        String sanctions_end_date_str = request.getParameter("sanctions_end_date");


        return result;
    }
}
