package com.ama.don.community.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentCommentCreateDto {
	private Long user_id;
	private String comment_content;
	private Long targetId;
	private String targetType;
	private Long parent_comment_id;

	public String toString() {
		return "CommunityCommentCreateDto{" + "userId=" + user_id + ", commentContent='" + comment_content + '\''
				+ ", targetId=" + targetId + ", targetType='" + targetType + '\'' + ", parentCommentId="
				+ parent_comment_id + '}';
	}
}
