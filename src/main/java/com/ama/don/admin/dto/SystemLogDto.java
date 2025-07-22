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
public class SystemLogDto {
    private long system_log_id;
    private String system_log_level;
    private String system_log_message;
    private Timestamp system_log_occurred_at;
    private String system_log_stack_trace;
}
