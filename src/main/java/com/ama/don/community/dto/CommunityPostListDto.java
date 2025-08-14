package com.ama.don.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostListDto {
	private Long postId;
	private Long targetId; // review_id / housephoto_id / housedecoration_id
	private String targetType; // 'COMMUNITY_REVIEW' / 'COMMUNITY_HOUSEPHOTO' / 'COMMUNITY_HOUSEDECORATION'
	private String title; // *_title
	private String content; // *_content
//	private java.sql.Timestamp createdAt; // post.created_at
	private int viewCount; // *_count
	private int likeCount; // *_like_count
	private int commentCount; // comments count (is_deleted=0)
}