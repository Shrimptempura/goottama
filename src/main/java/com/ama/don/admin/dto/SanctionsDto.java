package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SanctionsDto {
    private int sanctionsId;
    private long userId;
    private String sanctionsTypes;
    private Timestamp sanctionsStartDate;
    private Timestamp sanctionsEndDate;
    private String sanctionsReason;
    private int adminAccountId;
    private Timestamp sanctionsCreatedAt;
}
