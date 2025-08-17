package com.ama.don.admin.service.adminDashboard;

import com.ama.don.admin.dao.*;
import com.ama.don.admin.dto.reportDTO.ReportDTO;
import com.ama.don.admin.dto.sanctionsDTO.SanctionsDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final ManageUserIDao manageUserIDao;
    private final PostForAdminIDao postForAdminIDao;
    private final SearchCommentIDao searchCommentIDao;
    private final SanctionsIDao sanctionsIDao;
    private final ManageReportsIDao manageReportsIDao;

    public AdminDashboardService(ManageUserIDao manageUserIDao,
                                 PostForAdminIDao postForAdminIDao,
                                 SearchCommentIDao searchCommentIDao,
                                 SanctionsIDao sanctionsIDao,
                                 ManageReportsIDao manageReportsIDao) {
        this.manageUserIDao = manageUserIDao;
        this.postForAdminIDao = postForAdminIDao;
        this.searchCommentIDao = searchCommentIDao;
        this.sanctionsIDao = sanctionsIDao;
        this.manageReportsIDao = manageReportsIDao;
    }

    public void execute(Model model) {
        // 현재 로그인 한 관리자 정보
        String adminId = (String) model.getAttribute("adminId");
        UserTotalDataDTO adminData = manageUserIDao.getUserByUserId(adminId);
        model.addAttribute("adminData", adminData);

        // 최근 24시간 및 1주일 신규 회원 수 조회
        int newUsersToday = manageUserIDao.countNewUsersLast24Hours();
        int newUsersThisWeek = manageUserIDao.countNewUsersLast7Days();
        model.addAttribute("newUsersToday", newUsersToday);
        model.addAttribute("newUsersThisWeek", newUsersThisWeek);

        // 일별 회원 가입 추이 데이터 조회
        List<Map<String, Object>> dailyRegistrations = manageUserIDao.getDailyUserRegistrations();
        model.addAttribute("dailyRegistrations", dailyRegistrations);

        // 최근 24시간 동안 올라온 새 글 수
        int newPostToday = postForAdminIDao.countNewPostsLast24Hours();
        model.addAttribute("newPostToday", newPostToday);

        // 최근 24시간 동안 올라온 새 댓글 수
        int newCommentToday = searchCommentIDao.countNewCommentsLast24Hours();
        model.addAttribute("newCommentToday", newCommentToday);

        // 최근 5개 재제 내역
        List<Map<String, Object>> sanctionList = new ArrayList<>();
        List<SanctionsDTO> sanctionsDTOS;
        sanctionsDTOS = sanctionsIDao.recent5Sanctions();
        for (SanctionsDTO dto : sanctionsDTOS) {
            Map<String, Object> row = new HashMap<>();
            row.put("sanctionsId", dto.getSanctions_id());
            row.put("userId", dto.getUser_id());
            row.put("sanctionsTypes", dto.getSanctions_types());
            row.put("sanctionsEndDate", dto.getSanctions_end_date());
            sanctionList.add(row);
        }
        model.addAttribute("sanctionList", sanctionList);

        // 최근 5개 신고 내역
        List<Map<String, Object>> reportList = new ArrayList<>();
        List<ReportDTO> reportDTOS;
        reportDTOS = manageReportsIDao.recent5Reports();
        for (ReportDTO dto : reportDTOS) {
            Map<String, Object> row = new HashMap<>();
            row.put("reportId", dto.getReportId());
            row.put("userId", dto.getUserId());
            row.put("reportDate", dto.getReportDate());
            row.put("reportContent", dto.getReportContent());
            row.put("targetType", dto.getTargetType());
            row.put("targetId", dto.getTargetId());
            row.put("reportStatus", dto.getReportStatus());
            reportList.add(row);
        }
        model.addAttribute("reportList", reportList);

        // 진행중인 신고 건 수
        int inProgressingReports = manageReportsIDao.countInProgressingReports();
        model.addAttribute("inProgressingReports", inProgressingReports);
    }
}
