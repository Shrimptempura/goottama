package com.ama.don.admin.dto.reportDTO.wrapperForReport;

import com.ama.don.admin.dto.noticeDTO.NoticesDto;
import com.ama.don.admin.dto.reportDTO.ReportedItem;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class NoticeReportedItem implements ReportedItem {

    private final NoticesDto noticesDto;

    public NoticeReportedItem(NoticesDto noticesDto) {
        this.noticesDto = noticesDto;
    }

    @Override
    public String getId() {
        String id = String.valueOf(noticesDto.getNotices_id());
        return id;
    }

    @Override
    public String getType() {
        return "NOTICE";
    }

    @Override
    public String getTitle() {
        String title = noticesDto.getNotices_title();
        return title;
    }

    @Override
    public String getContent() {
        String content = noticesDto.getNotices_content();
        return content;
    }

    @Override
    public String getAuthorId() {
        return "관리자";
    }

    @Override
    public String getAuthorNickname() {
        return null;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        Timestamp createdAt = noticesDto.getNotices_created_at();
        Instant instant = createdAt.toInstant();
        return instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}
