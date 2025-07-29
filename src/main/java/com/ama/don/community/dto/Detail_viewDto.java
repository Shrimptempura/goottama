package com.ama.don.community.dto;

import java.sql.Timestamp;

import com.ama.don.common.enums.TargetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detail_viewDto {

	private Long post_id;
	private Long user_id;
	private String post_title;
	private String post_content;
	private Timestamp post_date;
	private int post_count;
	private int post_like_count;
	private String post_img;

	private TargetType target_type;

	private int target_id;

}
