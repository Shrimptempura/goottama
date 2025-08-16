package com.ama.don.admin.controller;

import com.ama.don.admin.dto.userDTO.WithdrawalReasonSearchDTO;
import com.ama.don.admin.service.withrawalReason.GetWithdrawalDetail;
import com.ama.don.admin.service.withrawalReason.GetWithdrawalListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WithdrawalReasonController {

    private final GetWithdrawalListService getWithdrawalListService;
    private final GetWithdrawalDetail getWithdrawalDetail;

    public WithdrawalReasonController(GetWithdrawalListService getWithdrawalListService,
                                      GetWithdrawalDetail getWithdrawalDetail) {
        this.getWithdrawalListService = getWithdrawalListService;
        this.getWithdrawalDetail = getWithdrawalDetail;
    }

    @GetMapping("/admin/withdrawal/withdrawal_reason_page")
    public String withdrawalPage(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute WithdrawalReasonSearchDTO withdrawalReasonSearchDTO){
        // 초기화
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (withdrawalReasonSearchDTO == null) {
            withdrawalReasonSearchDTO = new WithdrawalReasonSearchDTO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("withdrawalReasonSearchDTO", withdrawalReasonSearchDTO);

        getWithdrawalListService.execute(model);
        return "admin/withrawal_reason/withdrawal_reason_page";
    }

    @PostMapping("/admin/withdrawal/withdrawal_reason_list")
    public String withdrawalList(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute WithdrawalReasonSearchDTO withdrawalReasonSearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("withdrawalReasonSearchDTO", withdrawalReasonSearchDTO);
        getWithdrawalListService.execute(model);

        return "admin/withrawal_reason/withdrawal_reason_list";
    }

    @GetMapping("/admin/withdrawal/withdrawal_reason_modal")
    public String withdrawalDataModal(Model model, @RequestParam("withdrawalId") Long withdrawalId){
        model.addAttribute("withdrawalId", withdrawalId);
        getWithdrawalDetail.execute(model);
        return "admin/withrawal_reason/withdrawal_reason_modal";
    }
}
