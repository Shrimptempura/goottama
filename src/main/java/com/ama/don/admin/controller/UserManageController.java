package com.ama.don.admin.controller;

import com.ama.don.admin.dto.userDTO.UserSearchDTO;
import com.ama.don.admin.service.sanctionService.CreateSanction;
import com.ama.don.admin.service.userManage.ChangeUserRoleService;
import com.ama.don.admin.service.userManage.GetUserDataForModal;
import com.ama.don.admin.service.userManage.GetUserDetailData;
import com.ama.don.admin.service.userManage.GetUserListService;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.interior.dev.DevFindTarget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserManageController {

    private final GetUserListService getUserListService;
    private final GetUserDataForModal getUserDataForModal;
    private final GetUserDetailData getUserDetailData;
    private final ChangeUserRoleService changeUserRoleService;
    private final CreateSanction createSanction;

    public UserManageController(ChangeUserRoleService changeUserRoleService,
                                CreateSanction createSanction,
                                GetUserDetailData getUserDetailData,
                                GetUserListService getUserListService,
                                GetUserDataForModal getUserDataForModal) {
        this.getUserListService = getUserListService;
        this.getUserDataForModal = getUserDataForModal;
        this.getUserDetailData = getUserDetailData;
        this.createSanction = createSanction;
        this.changeUserRoleService = changeUserRoleService;
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

        String sanctionReason = "관리자에 의한 재제 종료일 변경";
        String sanctionType = "변경";
        String adminId = String.valueOf(DevFindTarget.getUserId());

        boolean isSuccess = createSanction.createSanctionFromChange(userId, userSanctionsUntil, sanctionType, sanctionReason, adminId);

        String resultMessage = isSuccess ? "change_user_sanctions_until_success" : "change_user_sanctions_until_failure";
        return "redirect:/admin/users/user_data_detail?user_id=" + userId + "&result=" + resultMessage;
    }

    @GetMapping("/admin/users/roles_modal_content")
    public String changeUserRoleView(@RequestParam("userInfo") String userInfo, Model model) {
        String[] infoArray = userInfo.split(",");

        String userId = infoArray[0];
        String rolesId = infoArray[1];

        model.addAttribute("rolesId", rolesId);
        model.addAttribute("userId", userId);
        return "admin/users/user_role_modal";
    }

    @PostMapping("/admin/users/change_user_role")
    public String changeUserRole(Model model,
                                 @RequestParam("user_id") String userId,
                                 @RequestParam("new_user_role") String userRole) {
        model.addAttribute("userId", userId);
        model.addAttribute("userRole", userRole);
        boolean isSuccess = changeUserRoleService.execute(model);
        String resultMessage = isSuccess ? "change_user_role_success" : "change_user_role_failure";
        return "redirect:/admin/users/user_data_detail?user_id=" + userId + "&result=" + resultMessage;
    }
}
