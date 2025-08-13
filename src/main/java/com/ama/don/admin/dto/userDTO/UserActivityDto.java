package com.ama.don.admin.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDto {
    private long user_activity_id;
    private long user_id;
    private String user_activity_type;
    private Timestamp user_activity_time;
    private Long user_activity_target_id;
    private String user_activity_target_type;
    private String user_activity_details;
}
