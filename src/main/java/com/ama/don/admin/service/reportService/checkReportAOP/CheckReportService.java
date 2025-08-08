package com.ama.don.admin.service.reportService.checkReportAOP;

import com.ama.don.admin.dao.ManageReportsIDao;
import org.springframework.stereotype.Service;

/**
 * 게시글, 공지, 댓글 등 텍스트 컨텐츠들이 신고 되어있는 상태인지 확인
 */
@Service
public class CheckReportService {

    private final ManageReportsIDao manageReportsIDao;

    public CheckReportService(ManageReportsIDao manageReportsIDao) {
        this.manageReportsIDao = manageReportsIDao;
    }

    public boolean isReported(String targetType, Long targetId){
        String status = manageReportsIDao.getReportStatusByTarget(targetType, targetId);
        if (status == null) {
            return false;
        }
        if (status.equals("PENDING") || status.equals("IN_REVIEW")) {
            return true;
        }
        return false;
    }
}
