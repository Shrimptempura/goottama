package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 공지 검색 전용 VO  <br>
 * 검색을 위한 날짜 범위를 noticeDateStart와 noticeDateEnd로 받음
 */
@Setter
@Getter
@AllArgsConstructor
public class NoticeSearchVO {
    private String noticeTitle;
    private String noticeContent;
    private Timestamp noticeDateStart;
    private Timestamp noticeDateEnd;
}
