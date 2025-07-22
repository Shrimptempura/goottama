package com.ama.don.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserSearchVO {
    private String user_name;
    private String user_nickname;
    private String user_gender;
    private String user_tel;
    private String user_addr;
    private String user_email;
    private String search_zipcode;
    private Timestamp birth_start_date;
    private Timestamp birth_end_date;
    private Timestamp create_start_date;
    private Timestamp create_end_date;
}
