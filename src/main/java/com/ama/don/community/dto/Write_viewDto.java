package com.ama.don.community.dto;

import com.ama.don.common.enums.TargetType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Write_viewDto {
	private Long post_id;
	private int user_id;
	private String post_title;
	private String post_content;
	private String post_img;
	private TargetType target_type;
	private int target_id;
}
