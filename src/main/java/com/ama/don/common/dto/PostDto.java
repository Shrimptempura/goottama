package com.ama.don.common.dto;

import java.sql.Timestamp;
import java.util.List;

import com.ama.don.common.enums.TargetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Long post_id; // 게시글 아이디

	private Long user_id;
	private String post_title; // 게시글 제목
	private String post_content; // 게시글 내용
	private Timestamp post_date; // 게시글 작성 날짜
	private int post_count; // 게시글 조회수
	private int post_like_count; // 게시글 좋아요 수

	private Long targetId; // 대상 아이디
	private TargetType targetType; // enum: INTERIOR, COMMUNITY

	// 커뮤니티 이미지 (파일은 파일 공통 Dto에서 처리)
	private List<FileDto> fileList;

}
