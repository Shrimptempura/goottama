package com.ama.don.admin.dto.postForAdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostForAdminDTO {
    private Long post_id; // 게시글 아이디
    private Long user_id;
    private String post_title; // 게시글 제목
    private String post_content; // 게시글 내용
    private String post_date; // 게시글 작성 날짜
    private Long targetId; // 대상 아이디
    private String targetType;
}
