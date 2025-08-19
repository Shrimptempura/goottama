package com.ama.don.admin.dto.reportDTO.wrapperForReport;

import com.ama.don.admin.dto.reportDTO.ReportedItem;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberReportedItem implements ReportedItem {

    private final UserTotalDataDTO memberDto;

    public MemberReportedItem(UserTotalDataDTO memberDto) {
        this.memberDto = memberDto;
    }

    @Override
    public String getId() {
        return String.valueOf(memberDto.getUser_id());
    }

    @Override
    public String getType() {
        return "MEMBER";
    }

    @Override
    public String getTitle() {
        return memberDto.getLogin_id();
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getAuthorId() {
        return null;
    }

    @Override
    public String getAuthorNickname() {
        return memberDto.getUser_nickname();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        String userCreatedAt = memberDto.getUser_created_at();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(userCreatedAt, formatter);
    }
}
