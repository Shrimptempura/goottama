package com.ama.don.admin.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalReasonSearchDTO {
    Long withdraw_id;
    // 기간 검색을 위한 날짜 조건
    Timestamp withdrawal_date_start;
    Timestamp withdrawal_date_end;
    List<Integer> withdrawal_reason_id;
    String withdrawal_reason;
}
