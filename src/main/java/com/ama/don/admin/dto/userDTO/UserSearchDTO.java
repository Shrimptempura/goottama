package com.ama.don.admin.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDTO {
    private String user_name;
    private String user_nickname;
    private String user_gender;
    private String user_tel;
    private String user_addr;
    private String user_email;
    private String user_zipcode;
    private String create_start_date;
    private String create_end_date;
    private List<String> user_status;
}
