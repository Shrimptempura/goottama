package com.ama.don.admin.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTotalDataDTO {
    private Long user_id;
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
    private String sanctions_types; // sanctions 테이블에서 가지고 옴
    private String user_status;
    private String user_sanctions_until;
    private String user_password;
}
