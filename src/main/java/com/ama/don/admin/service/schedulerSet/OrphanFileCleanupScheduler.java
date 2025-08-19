package com.ama.don.admin.service.schedulerSet;

import com.ama.don.admin.service.noticeService.TUIImageControlService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrphanFileCleanupScheduler {

    private final TUIImageControlService tuiImageControlService;

    public OrphanFileCleanupScheduler(TUIImageControlService tuiImageControlService) {
        this.tuiImageControlService = tuiImageControlService;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupOrphanTuiEditorFiles() {
        System.out.println("### TUI 에디터 고아 파일 정리 배치 작업 시작 ###");
        tuiImageControlService.removeNegativeTargetIdFiles();
        System.out.println("### TUI 에디터 고아 파일 정리 배치 작업 완료 ###");
    }
}
