package com.ama.don.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/admin/admin_dashboard")
    public String adminDashboard(){
        return "/admin/admin_dashboard";
    }

    @RequestMapping("/admin/access_control")
    public String accessControl(){
        return "/admin/access_control";
    }

    @RequestMapping("/admin/advanced_search")
    public String advancedSearch(){
        return "/admin/advanced_search";
    }

    @RequestMapping("/admin/log_viewer")
    public String logViewer(){
        return "/admin/log_viewer";
    }

    @RequestMapping("/admin/notice_list")
    public String noticeList(){
        return "/admin/notice_list";
    }

    @RequestMapping("/admin/notice_detail")
    public String noticeDetail(){
        return "/admin/notice_detail";
    }

    @RequestMapping("/admin/notice_modify")
    public String noticeModify(){
        return "/admin/notice_modify";
    }

    @RequestMapping("/admin/notice_write")
    public String noticeWrite(){
        return "/admin/notice_write";
    }

    @RequestMapping("/admin/permission_setting")
    public String permissionSetting(){
        return "/admin/permission_setting";
    }

    @RequestMapping("/admin/report_list")
    public String reportList(){
        return "/admin/report_list";
    }

    @RequestMapping("/admin/report_detail")
    public String reportDetail(){
        return "/admin/report_detail";
    }

    @RequestMapping("/admin/statistics")
    public String statistics(){
        return "/admin/statistics";
    }

    @RequestMapping("/admin/sanctions")
    public String sanctions(){
        return "/admin/sanctions";
    }

    @RequestMapping("/admin/user_data_detail")
    public String userDataDetail(){
        return "/admin/user_data_detail";
    }

    @RequestMapping("/admin/user_manage")
    public String userManagement(){
        return "/admin/user_manage";
    }
}
