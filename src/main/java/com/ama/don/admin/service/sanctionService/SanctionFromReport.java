package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.ManageReportsIDao;
import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.SanctionsIDao;
import com.ama.don.admin.dto.sanctionsDTO.MakeSanctionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 신고 처리로부터 재제 정보를 받아서 변경
 */
@Service
public class SanctionFromReport {

    private final SanctionsIDao sanctionsIDao;
    private final ManageReportsIDao manageReportsIDao;
    private final ManageUserIDao manageUserIDao;

    public SanctionFromReport(SanctionsIDao sanctionsIDao, ManageReportsIDao manageReportsIDao, ManageUserIDao manageUserIDao) {
        this.sanctionsIDao = sanctionsIDao;
        this.manageReportsIDao = manageReportsIDao;
        this.manageUserIDao = manageUserIDao;
    }

    @Transactional
    public boolean makeSanctionAndHandleReport(MakeSanctionDTO makeSanctionDTO, String reportId, String statusChanged) {
        // 제재 정보 생성 (INSERT)
        int sanctionResult = sanctionsIDao.makeSanction(makeSanctionDTO);
        if (sanctionResult == 0) {
            throw new RuntimeException("제재 정보 생성 실패");
        }
        // 회원 제재 기간 업데이트 (UPDATE)
        int userUpdateResult = manageUserIDao.updateUserSanctionsUntil(makeSanctionDTO.getUser_id(), makeSanctionDTO.getSanctions_end_date());
        if (userUpdateResult == 0) {
            throw new RuntimeException("사용자 제재 기간 업데이트 실패");
        }
        // 신고 상태 변경 (UPDATE)
        int reportUpdateResult = manageReportsIDao.handleReportStatus(reportId, statusChanged);
        if (reportUpdateResult == 0) {
            throw new RuntimeException("신고 상태 변경 실패");
        }
        return true;
    }
}
