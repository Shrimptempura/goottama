package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyCommentCreateDto {

    private Long companyCommentId;
    private Long userId;
    private Long companyPostId;

    private String content;
    private Long parentCommentId;       // 대댓글의 부모 id
}
