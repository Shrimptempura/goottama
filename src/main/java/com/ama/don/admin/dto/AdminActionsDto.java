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
public class AdminActionsDto {
    private int admin_actions_id;
    private String admin_actions_type;
    private String admin_actions_target;
    private Timestamp admin_actions_time;
    private String admin_actions_details;
}
