package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;

@Controller
@RequestMapping("/community")
public class Write_viewController {

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	@GetMapping("/write_view")
	public String write_form() {
		return "community/write_view";
	}

	@PostMapping("/write")
	@ResponseBody
	public Long write_post(@RequestBody PostDto post_dto) {

		post_dto.setUser_id(1L); // 임시 사용자 ID

		// not null 대응 임시 targetId 설정
		post_dto.setTargetId(0L);

		// 타겟타입 설정 (예: "POST", "REVIEW")
		try {
			TargetType target_type = post_dto.getTargetType(); // 이미 DTO 안에 들어있다고 가정
			post_dto.setTargetType(target_type);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("잘못된 게시판 유형입니다.");
		}

		// 게시글 저장 (post_id 생성)
		postDao.create(post_dto);

		// post_id와 target_id를 일치시켜서 업데이트
		post_dto.setTargetId(post_dto.getPost_id());
		postDao.update_target_id(post_dto);

		// post_id를 프론트에 반환
		return post_dto.getPost_id();
	}

}
