package com.ama.don.admin.dto;

import lombok.Getter;
import lombok.Setter;

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
    private String create_start_date;
    private String create_end_date;
}
