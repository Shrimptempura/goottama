package com.ama.don.community.dto.Comment;

import java.sql.Timestamp;

import com.ama.don.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentCreateDto {

	private Long comment_id;
	private Long user_id;
	private Long review_id;

	private String comment_content;
	private Long parent_comment_id;
	private Timestamp created_at;

	private Long targetId;
	private TargetType targetType;
}
