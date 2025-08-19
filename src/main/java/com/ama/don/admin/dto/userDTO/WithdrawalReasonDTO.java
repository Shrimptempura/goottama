package com.ama.don.admin.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalReasonDTO {
    Long withdraw_id;
    Timestamp withdrawal_date;
    Integer withdrawal_reason_id;
    String withdrawal_reason;
}
