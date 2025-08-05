package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.SanctionsDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetUserDetailData {

    private final ManageUserIDao manageUserIDao;
    private final SanctionsIDao sanctionsIDao;

    public GetUserDetailData(ManageUserIDao manageUserIDao, SanctionsIDao sanctionsIDao) {
        this.manageUserIDao = manageUserIDao;
        this.sanctionsIDao = sanctionsIDao;
    }

    public void execute(Model model) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        Map<String, Object> map = model.asMap();
        String userId = (String) map.get("userId");
        UserTotalDataDTO userTotalDataDTO = manageUserIDao.getUserByUserId(userId);
        List<SanctionsDTO> sanctionsDto = sanctionsIDao.getSanctionsByUserId(userId);

        if (sanctionsDto != null && !sanctionsDto.isEmpty()) {
            for (SanctionsDTO dto : sanctionsDto) {
                if (dto != null) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("sanctionsId", dto.getSanctions_id());
                    row.put("userId", dto.getUser_id());
                    row.put("sanctionsTypes", dto.getSanctions_types());
                    row.put("sanctionsStartDate", dto.getSanctions_start_date());
                    row.put("sanctionsEndDate", dto.getSanctions_end_date());
                    row.put("sanctionsReason", dto.getSanctions_reason());
                    row.put("adminAccountId", dto.getAdmin_account_id());
                    row.put("sanctionsCreatedAt", dto.getSanctions_created_at());
                    mapList.add(row);
                }
            }
        }

        model.addAttribute("userData", userTotalDataDTO);
        model.addAttribute("sanctions", mapList);
    }
}
