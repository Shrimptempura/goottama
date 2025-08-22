package com.ama.don.admin.controller;

import com.ama.don.admin.dto.userDTO.UserActivitySearchDTO;
import com.ama.don.admin.service.userActivityLog.GetUserActivityDetail;
import com.ama.don.admin.service.userActivityLog.GetUserActivityList;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserActivityLogController {

    private final GetUserActivityList getUserActivityList;
    private final GetUserActivityDetail getUserActivityDetail;

    public UserActivityLogController(GetUserActivityList getUserActivityList, GetUserActivityDetail getUserActivityDetail) {
        this.getUserActivityList = getUserActivityList;
        this.getUserActivityDetail = getUserActivityDetail;

    }

    @GetMapping("/admin/logs/logs_page")
    public String userActivityPage(Model model,
                                   @ModelAttribute SearchVO searchVO,
                                   @ModelAttribute UserActivitySearchDTO userActivitySearchDTO) {
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (userActivitySearchDTO == null) {
            userActivitySearchDTO = new UserActivitySearchDTO();
        }
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userActivitySearchDTO", userActivitySearchDTO);
        getUserActivityList.execute(model);
        return "admin/logs/logs_page";
    }

    @PostMapping("/admin/logs/log_list")
    public String userActivityList(Model model,
                                   @ModelAttribute SearchVO searchVO,
                                   @ModelAttribute UserActivitySearchDTO userActivitySearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userActivitySearchDTO", userActivitySearchDTO);
        getUserActivityList.execute(model);
        return "admin/logs/log_list";
    }

    @GetMapping("/admin/logs/log_detail")
    public String userActivityLogDetail(Model model,
                                        @RequestParam("user_activity_id") String userActivityId) {
        model.addAttribute("userActivityId", userActivityId);
        getUserActivityDetail.execute(model);
        return "admin/logs/log_detail";
    }

    @GetMapping("/admin/logs/user_log_data_modal")
    public String userActivityLogDataModal(Model model,
                                        @RequestParam("user_activity_id") String userActivityId) {
        model.addAttribute("userActivityId", userActivityId);
        getUserActivityDetail.execute(model);
        return "admin/logs/user_log_data_modal";
    }

}
