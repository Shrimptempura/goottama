package com.ama.don.interior.dto.comment;

import com.ama.don.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyCommentCreateDto {

    private Long commentId;
    private Long userId;
    private Long companyPostId;

    private String commentContent;
    private Long parentCommentId;       // 대댓글의 부모 id

    private Long targetId;
    private TargetType targetType;
}
