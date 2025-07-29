package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTotalDataVO {
    private long user_id;
    private String user_name;
    private String user_nickname;
    private char user_gender;
    private String user_birth;
    private String user_created_at;
    private String user_tel;
    private String user_zipcode;
    private String user_addr;
    private String user_email;
    private String user_img;
    private long roles_id;
    private String login_id;
    private String sanctions_types;
}
