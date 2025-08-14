package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.SanctionsDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.utils.DateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class GetSanctionDetail {

    private final SanctionsIDao sanctionsIDao;
    private final ManageUserIDao manageUserIDao;

    public GetSanctionDetail(ManageUserIDao manageUserIDao, SanctionsIDao sanctionsIDao) {
        this.sanctionsIDao = sanctionsIDao;
        this.manageUserIDao = manageUserIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        String sanctionId = (String) map.get("sanctionId");
        SanctionsDTO sanctionsDTO = sanctionsIDao.getSanctionsBySanctionId(sanctionId);
        if (sanctionsDTO == null) {
            throw new RuntimeException("재제 내역을 찾을 수 없음, ID : " + sanctionId);
        }

        String sanctionDuration = DateTimeUtil.calcDaysAndHours(sanctionsDTO.getSanctions_start_date(), sanctionsDTO.getSanctions_end_date());
        sanctionsDTO.setSanctions_duration(sanctionDuration);

        Long userId = sanctionsDTO.getUser_id();
        UserTotalDataDTO userTotalDataDTO = manageUserIDao.getUserByUserId(String.valueOf(userId));

        model.addAttribute("userData", userTotalDataDTO);
        model.addAttribute("list", sanctionsDTO);
    }
}
