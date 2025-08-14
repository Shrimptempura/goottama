package com.ama.don.admin.controller;

import com.ama.don.admin.dto.sanctionsDTO.SanctionSearchDTO;
import com.ama.don.admin.dto.sanctionsDTO.SanctionsDTO;
import com.ama.don.admin.dto.userDTO.UserActivitySearchDTO;
import com.ama.don.admin.service.sanctionService.*;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.interior.dev.DevFindTarget;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SanctionController {

    private final GetSanctionList getSanctionList;
    private final GetSanctionDetail getSanctionDetail;
    private final CreateSanction createSanction;
    private final DeleteSanction deleteSanction;

    public SanctionController(GetSanctionList getSanctionList,
                              GetSanctionDetail getSanctionDetail,
                              CreateSanction createSanction,
                              DeleteSanction deleteSanction) {
        this.getSanctionList = getSanctionList;
        this.getSanctionDetail = getSanctionDetail;
        this.createSanction = createSanction;
        this.deleteSanction = deleteSanction;
    }

    @GetMapping("/admin/sanctions/sanctions_page")
    public String sanctionPage(Model model,
                               @ModelAttribute SearchVO searchVO,
                               @ModelAttribute SanctionSearchDTO sanctionSearchDTO) {
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (sanctionSearchDTO == null) {
            sanctionSearchDTO = new SanctionSearchDTO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("sanctionSearchDTO", sanctionSearchDTO);
        getSanctionList.execute(model);
        return "admin/sanctions/sanctions_page";
    }

    @PostMapping("/admin/sanctions/sanction_list")
    public String sanctionList(Model model,
                               @ModelAttribute SearchVO searchVO,
                               @ModelAttribute SanctionSearchDTO sanctionSearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("sanctionSearchDTO", sanctionSearchDTO);
        getSanctionList.execute(model);
        return "admin/sanctions/sanction_list";
    }

    @GetMapping("/admin/sanctions/sanction_data_modal")
    public String sanctionDataModal(Model model,
                                    @RequestParam("sanctions_id") String sanctionId) {
        model.addAttribute("sanctionId", sanctionId);
        getSanctionDetail.execute(model);
        return "admin/sanctions/sanction_data_modal";
    }

    @GetMapping("/admin/sanctions/create_sanction_view")
    public String createNewSanctionView(Model model,
                                        @RequestParam("userId") String userId) {
        Long adminId = DevFindTarget.getUserId();
        model.addAttribute("userId", userId);
        model.addAttribute("adminId", adminId);
        return "admin/sanctions/create_sanction_modal";
    }

    @GetMapping("/admin/sanctions/create_sanction")
    public String createNewSanction(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        boolean isSuccess = createSanction.execute(model);
        String resultMessage = isSuccess ? "create_sanction_success" : "create_sanction_failure";
        return "redirect:/admin/admin_index?menu=sanctions&result=" + resultMessage;
    }

    @GetMapping("/admin/sanctions/delete_sanction")
    public String deleteSanctionBySanctionId(Model model,
                                 @RequestParam("sanctions_id") String sanctionId) {
        model.addAttribute("sanctionId", sanctionId);
        boolean isSuccess = deleteSanction.execute(model);
        String resultMessage = isSuccess ? "delete_sanction_success" : "delete_sanction_failure";
        return "redirect:/admin/admin_index?menu=sanctions&result=" + resultMessage;
    }

}
