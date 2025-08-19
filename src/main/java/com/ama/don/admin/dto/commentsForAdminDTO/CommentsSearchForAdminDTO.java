package com.ama.don.admin.dto.commentsForAdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsSearchForAdminDTO {
    Long commentId;
    Long userId;
    String commentContent;
    String createdAtStart;
    String createdAtEnd;
    Long targetId;
    List<String> targetType;
    String modifiedAtStart;
    String modifiedAtEnd;
    Integer isDeleted;
    Long parentCommentId;
}