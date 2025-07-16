package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 신고 검색을 위한 전용 VO <br>
 * 신고일 범위 검색을 위해 reportDateStart와 reportDateEnd을 받는다.
 */
@Setter
@Getter
@AllArgsConstructor
public class ReportSearchVO {
    private long userId;
    private String reportContent;
    private String targetType;
    private long targetId;
    private String reportStatus;
    private Timestamp reportDateStart;
    private Timestamp reportDateEnd;
}
