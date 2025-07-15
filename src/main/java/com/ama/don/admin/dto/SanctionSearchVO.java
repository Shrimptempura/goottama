package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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

