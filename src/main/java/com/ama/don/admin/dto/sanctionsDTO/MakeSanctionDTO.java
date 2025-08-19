package com.ama.don.admin.dto.sanctionsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MakeSanctionDTO {
    private String user_id;
    private String sanctions_types;
    private Timestamp sanctions_start_date;
    private Timestamp sanctions_end_date;
    private String sanctions_reason;
    private String admin_account;
}
