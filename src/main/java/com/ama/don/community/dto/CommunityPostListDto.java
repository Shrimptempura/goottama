package com.ama.don.community.dto;

import java.sql.Timestamp;
import com.ama.don.common.enums.TargetType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostListDto {
	private Long post_id; 
	private String post_title; 
	private String post_content; 
	private Timestamp post_date; 
	private int view_count; 
	private int like_count; 
	private int comment_count; 

	private Long targetId;
	private TargetType targetType;
}
