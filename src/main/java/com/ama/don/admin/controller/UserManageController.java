package com.ama.don.admin.controller;

import com.ama.don.admin.dto.UserSearchVO;
import com.ama.don.admin.service.userManage.GetUserListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserManageController {

    private final GetUserListService getUserListService;

    public UserManageController(GetUserListService getUserListService) {
        this.getUserListService = getUserListService;
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
}
