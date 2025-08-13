package com.ama.don.admin.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivitySearchDTO {
    private long user_activity_id;
    private long user_id;
    private String user_activity_type;
    private String user_activity_time_start;
    private String user_activity_time_end;
    private String user_activity_target;
    private String user_activity_details;
}
