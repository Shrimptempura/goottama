package com.ama.don.admin.dto.sanctionsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SanctionsDTO {
    private int sanctions_id;
    private long user_id;
    private String sanctions_types;
    private Timestamp sanctions_start_date;
    private Timestamp sanctions_end_date;
    private String sanctions_reason;
    private int admin_account_id;
    private Timestamp sanctions_created_at;
}
