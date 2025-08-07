package com.ama.don.admin.dto.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubmitReportForm {
    private String userId;
    private String reportContent;
    private String targetType;
    private String targetId;
}
