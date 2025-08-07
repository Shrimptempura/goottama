package com.ama.don.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("admin/admin_index")
    public String adminIndex(Model model){
        model.addAttribute("leftNavigationBar", "leftNavigationBar.jsp");
        return "admin/admin_index";
    }

    @RequestMapping("admin/admin_dashboard")
    public String dashboard(){
        return "admin/admin_dashboard";
    }

    @RequestMapping("admin/reports/report_page")
    public String report(){
        return "admin/reports/report_page";
    }

    @RequestMapping("admin/search/advanced_search")
    public String advancedSearch(){
        return "admin/search/advanced_search";
    }

    @RequestMapping("admin/log/log_viewer")
    public String log(){
        return "admin/log/log_viewer";
    }

    @RequestMapping("admin/statistics/statistics")
    public String statistics(){
        return "admin/statistics/statistics";
    }

    @RequestMapping("admin/access_control/access_control")
    public String accessControl(){
        return "admin/access_control/access_control";
    }

    @RequestMapping("admin/permission/permission_setting")
    public String permission(){
        return "admin/permission/permission_setting";
    }
}
