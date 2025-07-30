package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체 점수 조정 관리 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyScoreAdjustDto {

    private Long companyId;

    private int oldCommunicationRate;  // 소통 점수
    private int oldPriceRate;          // 가격 점수
    private int oldResultRate;         // 결과 점수
    private int oldScheduleRate;       // 일정 점수

    private int communicationRate;  // 소통 점수
    private int priceRate;          // 가격 점수
    private int resultRate;         // 결과 점수
    private int scheduleRate;       // 일정 점수
}
