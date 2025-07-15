package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
