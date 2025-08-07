package com.ama.don.community.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

	private Long comment_id;
	private Long parent_comment_id; // 대댓글
	private Long user_id;

	private String user_nickname;
	private String comment_content;
	private LocalDateTime created_at;
	private LocalDateTime modified_at;
	private boolean is_deleted;

	// 댓글 이미지
	private String user_profile_img_path;
	private String user_profile_img_name;
}
