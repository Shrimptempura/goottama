package com.ama.don.admin.controller;

import com.ama.don.admin.service.adminDashboard.AdminDashboardService;
import com.ama.don.interior.dev.DevFindTarget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private final AdminDashboardService adminDashboardService;

    public AdminController(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    @RequestMapping("admin/admin_index")
    public String adminIndex(Model model){
        model.addAttribute("leftNavigationBar", "leftNavigationBar.jsp");
        return "admin/admin_index";
    }

    @GetMapping("/admin/admin_dashboard")
    public String adminDashboard(Model model){
        String adminId = String.valueOf(DevFindTarget.getUserId());
        model.addAttribute("adminId", adminId);
        adminDashboardService.execute(model);
        return "admin/admin_dashboard";
    }
}

