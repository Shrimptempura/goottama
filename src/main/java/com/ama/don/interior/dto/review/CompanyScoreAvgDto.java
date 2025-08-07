package com.ama.don.interior.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 최소 한번 점수 입력후 사용(double)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyScoreAvgDto {

    private Long companyId;

    private double avgCommunication;
    private double avgResult;
    private double avgSchedule;
    private double avgPrice;

    private double avgTotalRate;
}
