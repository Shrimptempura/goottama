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
public class UserActivitySearchDTO {
    private long user_activity_id;
    private long user_id;
    private List<String> user_activity_type;
    private Timestamp user_activity_time_start;
    private Timestamp user_activity_time_end;
    private List<String> user_activity_target;
    private String user_activity_details;
}
