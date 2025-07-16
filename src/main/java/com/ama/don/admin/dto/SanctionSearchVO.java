package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 제재내역 검색을 위한 전용 VO <br>
 * 날짜 범위 검색을 위해 searchStartDate과 searchEndDate을 받는다. <br>
 * 제재 기간 범위 검색을 위해 durationMin과  durationMax을 받는다.<br>
 * 위 범위 검색을 위한 값들은 같은 값이 들어오면 범위가 아닌 equal 혹은 == 과 같다.
 */
@Setter
@Getter
@AllArgsConstructor
public class SanctionSearchVO {
    private long userId;
    private String sanctionsTypes;
    private Timestamp searchStartDate;
    private Timestamp searchEndDate;
    private String sanctionsReason;
    private int adminAccountId;
    private int durationMin;
    private int durationMax;
}

