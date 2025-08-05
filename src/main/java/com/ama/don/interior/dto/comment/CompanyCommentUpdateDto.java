package com.ama.don.interior.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 댓글 수정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyCommentUpdateDto {

    private Long commentId;
    private String commentContent;
    private Long userId;
}
