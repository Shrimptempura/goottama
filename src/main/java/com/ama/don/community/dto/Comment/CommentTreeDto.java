package com.ama.don.community.dto.Comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentTreeDto {
	private Long comment_id;
	private Long user_id;
	private Long parent_commentId;
	private String comment_content;
	private LocalDateTime created_at;
	private LocalDateTime modified_at;
	private Boolean is_deleted;
}