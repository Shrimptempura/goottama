package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class ReportDTO {
    private long reportId;
    private long userId;
    private Timestamp reportDate;
    private String reportContent;
    private int targetType;
    private long targetId;
    private String reportStatus;
}
