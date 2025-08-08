package com.ama.don.admin.controller;

import com.ama.don.admin.dto.userDTO.UserSearchDTO;
import com.ama.don.admin.service.userManage.ChangeUserSanctionsUntilService;
import com.ama.don.admin.service.userManage.GetUserDataForModal;
import com.ama.don.admin.service.userManage.GetUserDetailData;
import com.ama.don.admin.service.userManage.GetUserListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserManageController {
    // TODO: 날짜 확인 해서 지난 user_sanctions_until은 없애야
    private final GetUserListService getUserListService;
    private final GetUserDataForModal getUserDataForModal;
    private final GetUserDetailData getUserDetailData;
    private final ChangeUserSanctionsUntilService changeUserSanctionsUntilService;

    public UserManageController(ChangeUserSanctionsUntilService changeUserSanctionsUntilService, GetUserDetailData getUserDetailData, GetUserListService getUserListService, GetUserDataForModal getUserDataForModal) {
        this.getUserListService = getUserListService;
        this.getUserDataForModal = getUserDataForModal;
        this.getUserDetailData = getUserDetailData;
        this.changeUserSanctionsUntilService = changeUserSanctionsUntilService;
    }

    @RequestMapping("/admin/users/user_manage")
    public String userPage(Model model,
                           @ModelAttribute SearchVO searchVO,
                           @ModelAttribute UserSearchDTO userSearchDTO) {
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (userSearchDTO == null) {
            userSearchDTO = new UserSearchDTO();
        }
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userSearchDTO", userSearchDTO);
        getUserListService.execute(model);
        return "admin/users/user_manage";
    }

    @PostMapping("/admin/users/user_list")
    public String userList(Model model,
                           @ModelAttribute SearchVO searchVO,
                           @ModelAttribute UserSearchDTO userSearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userSearchDTO", userSearchDTO);
        getUserListService.execute(model);
        return "admin/users/user_list";
    }

    @GetMapping("/admin/users/user_data_modal")
    public String userDataModal(Model model, @RequestParam("userId") String userId) {
        model.addAttribute("userId", userId);
        getUserDataForModal.execute(model);
        return "admin/users/user_data_modal";
    }

    @GetMapping("/admin/users/user_data_detail")
    public String userDetail(Model model, @RequestParam("user_id") String userId) {
        model.addAttribute("userId", userId);
        getUserDetailData.execute(model);
        return "admin/users/user_data_detail";
    }

    @GetMapping("/admin/users/sanction_modal_content")
    public String getSanctionModalContent(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/users/sanction_modal_content";
    }

    @PostMapping("/admin/users/change_user_sanctions_until")
    public String changeUserSanctionsUntil(Model model,
                                           @RequestParam("user_id") String userId,
                                           @RequestParam("user_sanctions_until") String userSanctionsUntil,
                                           @RequestParam("new_user_status") String userStatus) {
        model.addAttribute("userId", userId);
        model.addAttribute("userSanctionsUntil", userSanctionsUntil);
        model.addAttribute("userStatus", userStatus);
        boolean isSuccess = changeUserSanctionsUntilService.execute(model);
        String resultMessage = isSuccess ? "change_user_sanctions_until_success" : "change_user_sanctions_until_failure";
        return "redirect:/admin/users/user_data_detail?user_id=" + userId + "&result=" + resultMessage;
    }
}
