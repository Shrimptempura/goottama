package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.SanctionSearchDTO;
import com.ama.don.admin.dto.sanctionsDTO.SanctionsDTO;
import com.ama.don.admin.utils.DateTimeUtil;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetSanctionList {

    private final SanctionsIDao sanctionsIDao;

    public GetSanctionList(SanctionsIDao sanctionsIDao) {
        this.sanctionsIDao = sanctionsIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        SanctionSearchDTO sanctionSearchDTO = (SanctionSearchDTO) map.get("sanctionSearchDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<SanctionsDTO> dtoList;
        int total;

        if (sanctionSearchDTO == null ||
                (sanctionSearchDTO.getUserId() == null || sanctionSearchDTO.getUserId() == 0) &&
                (sanctionSearchDTO.getSanctionsTypes() == null || sanctionSearchDTO.getSanctionsTypes().isEmpty()) &&
                (sanctionSearchDTO.getSearchStartDate() == null || sanctionSearchDTO.getSearchStartDate().isEmpty()) &&
                (sanctionSearchDTO.getSearchEndDate() == null || sanctionSearchDTO.getSearchEndDate().isEmpty()) &&
                (sanctionSearchDTO.getSanctionsReason() == null || sanctionSearchDTO.getSanctionsReason().isEmpty()) &&
                (sanctionSearchDTO.getAdminAccountId() == null || sanctionSearchDTO.getAdminAccountId() == 0) &&
                (sanctionSearchDTO.getDurationMin() == null || sanctionSearchDTO.getDurationMin() == 0) &&
                (sanctionSearchDTO.getDurationMax() == null || sanctionSearchDTO.getDurationMax() == 0)) {
            total = sanctionsIDao.countAllSanctions();
            dtoList = sanctionsIDao.getAllSanctions(searchVO);
        } else {
            total = sanctionsIDao.countSearchSanctions(sanctionSearchDTO);
            dtoList = sanctionsIDao.searchSanctions(searchVO, sanctionSearchDTO);
        }

        searchVO.pageCalculate(total);

        for (SanctionsDTO dto : dtoList) {
            Map<String, Object> row = new HashMap<>();
            row.put("sanctionsId", dto.getSanctions_id());
            row.put("userId", dto.getUser_id());
            row.put("sanctionsTypes", dto.getSanctions_types());
            row.put("sanctionsStartDate", dto.getSanctions_start_date());
            row.put("sanctionsEndDate", dto.getSanctions_end_date());
            row.put("sanctionsReason", dto.getSanctions_reason());
            row.put("adminAccountId", dto.getAdmin_account_id());
            row.put("sanctionsCreatedAt", dto.getSanctions_created_at());
            String sanctionDuration = DateTimeUtil.calcDaysAndHours(dto.getSanctions_start_date(), dto.getSanctions_end_date());
            dto.setSanctions_duration(sanctionDuration);
            row.put("sanctionDuration", dto.getSanctions_duration());
            mapList.add(row);
        }

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);
    }
}
