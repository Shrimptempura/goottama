package com.ama.don.admin.controller;

import com.ama.don.admin.dto.UserSearchDTO;
import com.ama.don.admin.service.userManage.GetUserDataForModal;
import com.ama.don.admin.service.userManage.GetUserDetailData;
import com.ama.don.admin.service.userManage.GetUserListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserManageController {

    private final GetUserListService getUserListService;
    private final GetUserDataForModal getUserDataForModal;
    private final GetUserDetailData getUserDetailData;

    public UserManageController(GetUserDetailData getUserDetailData, GetUserListService getUserListService, GetUserDataForModal getUserDataForModal) {
        this.getUserListService = getUserListService;
        this.getUserDataForModal = getUserDataForModal;
        this.getUserDetailData = getUserDetailData;
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
        System.out.println("\n>>> userstatus : "+userSearchDTO.getUser_status());
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
}
