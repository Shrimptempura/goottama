package com.ama.don.admin.dto.noticeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공지 검색 전용 VO  <br>
 * 검색을 위한 날짜 범위를 noticeDateStart와 noticeDateEnd로 받음
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeSearchDTO {
    private String noticeTitle;
    private String noticeContent;
    private String noticeDateStart;
    private String noticeDateEnd;
}
