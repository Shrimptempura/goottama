package com.ama.don.admin.service.withrawalReason;

import com.ama.don.admin.dao.WithdrawalReasonForAdminIDao;
import com.ama.don.admin.dto.userDTO.WithdrawalReasonDTO;
import com.ama.don.admin.dto.userDTO.WithdrawalReasonSearchDTO;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetWithdrawalListService {

    private final WithdrawalReasonForAdminIDao withdrawalReasonForAdminIDao;

    public GetWithdrawalListService(WithdrawalReasonForAdminIDao withdrawalReasonForAdminIDao) {
        this.withdrawalReasonForAdminIDao = withdrawalReasonForAdminIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        WithdrawalReasonSearchDTO withdrawalReasonSearchDTO = (WithdrawalReasonSearchDTO) map.get("withdrawalReasonSearchDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<WithdrawalReasonDTO> withdrawalReasonDTOS;
        int total;

        // 검색 조건이 없거나 비어있으므로 전체 탈퇴 사유를 가져옴
        if (withdrawalReasonSearchDTO == null ||
                (withdrawalReasonSearchDTO.getWithdrawal_reason() == null || withdrawalReasonSearchDTO.getWithdrawal_reason().isEmpty()) &&
                        withdrawalReasonSearchDTO.getWithdraw_id() == null &&
                        (withdrawalReasonSearchDTO.getWithdrawal_reason_id() == null || withdrawalReasonSearchDTO.getWithdrawal_reason_id().isEmpty()) && // isEmpty() 추가
                        (withdrawalReasonSearchDTO.getWithdrawal_date_start() == null || withdrawalReasonSearchDTO.getWithdrawal_date_start().isEmpty()) &&
                        (withdrawalReasonSearchDTO.getWithdrawal_date_end() == null || withdrawalReasonSearchDTO.getWithdrawal_date_end().isEmpty())) {
            total = withdrawalReasonForAdminIDao.countAllWithdrawalReason();
            withdrawalReasonDTOS = withdrawalReasonForAdminIDao.getAllWithdrawalReason(searchVO);
        // 검색 조건이 있으면 검색된 탈퇴 사유를 가져옴
        } else {
            total = withdrawalReasonForAdminIDao.countSearchWithdrawalReason(withdrawalReasonSearchDTO);
            withdrawalReasonDTOS = withdrawalReasonForAdminIDao.searchWithdrawalReason(searchVO, withdrawalReasonSearchDTO);
        }

        searchVO.pageCalculate(total);

        for (WithdrawalReasonDTO dto : withdrawalReasonDTOS) {
            Map<String, Object> row = new HashMap<>();
            row.put("withdraw_id", dto.getWithdraw_id());
            row.put("withdrawal_date", dto.getWithdrawal_date());
            row.put("withdrawal_reason_id", dto.getWithdrawal_reason_id());
            row.put("withdrawal_reason", dto.getWithdrawal_reason());
            mapList.add(row);
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("mapList", mapList);
    }
}
