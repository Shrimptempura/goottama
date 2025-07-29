package com.ama.don.admin.controller;

import com.ama.don.admin.dto.UserSearchVO;
import com.ama.don.admin.dto.UserTotalDataVO;
import com.ama.don.admin.service.userManage.GetUserDataForModal;
import com.ama.don.admin.service.userManage.GetUserListService;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserManageController {

    private final GetUserListService getUserListService;
    private final GetUserDataForModal getUserDataForModal;

    public UserManageController(GetUserListService getUserListService, GetUserDataForModal getUserDataForModal) {
        this.getUserListService = getUserListService;
        this.getUserDataForModal = getUserDataForModal;
    }

    @RequestMapping("/admin/users/user_manage")
    public String userPage(Model model,
                           @ModelAttribute SearchVO searchVO,
                           @ModelAttribute UserSearchVO userSearchVO) {
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (userSearchVO == null) {
            userSearchVO = new UserSearchVO();
        }
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userSearchVO", userSearchVO);
        getUserListService.execute(model);
        return "admin/users/user_manage";
    }

    @PostMapping("/admin/users/user_list")
    public String userList(Model model,
                           @ModelAttribute SearchVO searchVO,
                           @ModelAttribute UserSearchVO userSearchVO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userSearchVO", userSearchVO);
        getUserListService.execute(model);

        return "admin/users/user_list";
    }

    @GetMapping("/admin/users/user_data_modal")
    public String userDataModal(Model model, @RequestParam("userId") String userId) {
        model.addAttribute("userId", userId);
        getUserDataForModal.execute(model);
        return "admin/users/user_data_modal";
    }
}
