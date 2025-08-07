package com.ama.don.admin.dto.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class ReportDTO {
    private Long reportId;
    private Long userId;
    private Timestamp reportDate;
    private String reportContent;
    private String targetType;
    private Long targetId;
    private String reportStatus;
}
