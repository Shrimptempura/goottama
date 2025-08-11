package com.ama.don.admin.service.reportService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.NoticesIDao;
import com.ama.don.admin.dto.noticeDTO.NoticesDto;
import com.ama.don.admin.dto.reportDTO.ReportDTO;
import com.ama.don.admin.dto.reportDTO.wrapperForReport.MemberReportedItem;
import com.ama.don.admin.dto.reportDTO.wrapperForReport.NoticeReportedItem;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.common.dto.CommentsDto;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.dto.ReviewDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class GetReportDetail {

    private final ManageReportsIDao manageReportsIDao;
    private final ManageUserIDao manageUserIDao;
    private final NoticesIDao noticesIDao;

    public GetReportDetail(NoticesIDao noticesIDao, ManageReportsIDao manageReportsIDao, ManageUserIDao manageUserIDao) {
        this.manageReportsIDao = manageReportsIDao;
        this.manageUserIDao = manageUserIDao;
        this.noticesIDao = noticesIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");

        String reportId = request.getParameter("report_id");
        ReportDTO report = manageReportsIDao.getReportByReportId(reportId);

        if (report == null) {
            throw new RuntimeException("신고 내역 없음 ID : " + reportId);
        }

        String reporterId = String.valueOf(report.getUserId());
        UserTotalDataDTO reporter = manageUserIDao.getUserByUserId(reporterId);
        String reportTargetType = report.getTargetType();
        Long reportTargetId = report.getTargetId();

        if ("INTERIOR".equals(reportTargetType)) {

        } else if ("COMMUNITY".equals(reportTargetType)) {

        } else if ("COMMUNITY_REVIEW".equals(reportTargetType)) {

        } else if ("REVIEW".equals(reportTargetType)) {

        } else if ("COMMENT".equals(reportTargetType)) {

        } else if ("SHOP".equals(reportTargetType)) {

        } else if ("NOTICE".equals(reportTargetType)) {
            NoticesDto noticesDto = searchNotice(reportTargetId);
            model.addAttribute("reported", new NoticeReportedItem(noticesDto));
        } else if ("MEMBER".equals(reportTargetType)) {
            UserTotalDataDTO userTotalDataDTO = searchUser(reportTargetId);
            model.addAttribute("reported", new MemberReportedItem(userTotalDataDTO));
        } else if ("ECT".equals(reportTargetType)) {

        } else {
            throw new RuntimeException("[ERROR] 타겟 타입 전달 오류.");
        }

        model.addAttribute("report", report);
        model.addAttribute("reporter", reporter);
    }

    public UserTotalDataDTO searchUser(Long reportTargetId){
        String userId = String.valueOf(reportTargetId);
        return manageUserIDao.getUserByUserId(userId);
    }

    public NoticesDto searchNotice(Long reportTargetId){
        String noticeId = String.valueOf(reportTargetId);
        return noticesIDao.getNoticeById(noticeId);
    }

    public ReviewDto searchReview(Long reportTargetId) {
        return null;
    }

    public CommentsDto searchComment(Long reportTargetId) {
        return null;
    }

    public PostDto searchPost(Long reportTargetId) {
        return null;
    }
}
