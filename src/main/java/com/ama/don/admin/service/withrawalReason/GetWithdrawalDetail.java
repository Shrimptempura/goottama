package com.ama.don.admin.service.withrawalReason;

import com.ama.don.admin.dao.WithdrawalReasonForAdminIDao;
import com.ama.don.admin.dto.userDTO.WithdrawalReasonDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class GetWithdrawalDetail {

    private final WithdrawalReasonForAdminIDao withdrawalReasonForAdminIDao;

    public GetWithdrawalDetail(WithdrawalReasonForAdminIDao withdrawalReasonForAdminIDao) {
        this.withdrawalReasonForAdminIDao = withdrawalReasonForAdminIDao;
    }

    public void execute(Model model) {
        Long withdrawalId = (Long) model.getAttribute("withdrawalId");
        WithdrawalReasonDTO withdrawalReasonDTO = withdrawalReasonForAdminIDao.getAllWithdrawalReasonByWithdrawalId(withdrawalId);
        if (withdrawalReasonDTO == null) {
            throw new RuntimeException("탈퇴 이유를 찾을 수 없음. ID " + withdrawalId);
        }

        model.addAttribute("withdrawalReasonDTO", withdrawalReasonDTO);
    }
}
