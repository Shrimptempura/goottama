package com.ama.don.admin.dto.sanctionsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 제재내역 검색을 위한 전용 VO <br>
 * 날짜 범위 검색을 위해 searchStartDate과 searchEndDate을 받는다. <br>
 * 제재 기간 범위 검색을 위해 durationMin과  durationMax을 받는다.<br>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SanctionSearchDTO {
    private Long userId;
    private String sanctionsTypes;
    private String searchStartDate;
    private String searchEndDate;
    private String sanctionsReason;
    private Integer adminAccountId;
    private Integer durationMin;
    private Integer durationMax;
}

