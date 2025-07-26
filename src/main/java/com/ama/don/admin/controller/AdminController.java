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
    public String adminDashboard(){
        return "admin/admin_dashboard";
    }
}
