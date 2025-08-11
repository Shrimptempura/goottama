package com.ama.don.admin.service.schedulerSet;

import com.ama.don.admin.dao.ManageUserIDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public class SanctionScheduler {

    private final ManageUserIDao manageUserIDao;

    public SanctionScheduler(ManageUserIDao manageUserIDao) {
        this.manageUserIDao = manageUserIDao;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetExpiredSanctions() {
        System.out.println("제재 기간 만료 사용자 초기화 배치 작업 시작...");

        int updateCount = manageUserIDao.resetExpiredUserSanctions();
        if (updateCount > 0) {
            System.out.println("제재 기간 만료 사용자 초기화 성공 개수 : " + updateCount);
        } else {
            System.err.println("[ERROR] 제재 기간 만료 사용자 초기화 실패");
        }
    }
}
