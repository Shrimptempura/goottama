package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class NoticeSearchVO {
    private String noticeTitle;
    private String noticeContent;
    private Timestamp noticeDateStart;
    private Timestamp noticeDateEnd;
}
