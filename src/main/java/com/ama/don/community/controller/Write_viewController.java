package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dao.Write_viewDao;
import com.ama.don.community.dto.Write_viewDto;
import com.ama.don.common.dao.FileDao;

@Controller
@RequestMapping("/community")
public class Write_viewController {

	@Autowired
	private Write_viewDao write_viewDao;

	@Autowired
	private FileDao fileDao;

	private final String uploadDir = "C:/upload/";

	// 글쓰기 페이지
	@GetMapping("/write_view")
	public String writeForm() {
		return "community/write_view";
	}

	// 이미지 파일 분리 저장
	@PostMapping("/write")
	public String writePost(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("targetType") String targetType, @RequestParam("imgFiles") MultipartFile[] imgFiles,
			Model model) {

		Write_viewDto dto = new Write_viewDto();
		dto.setUser_id(1);
		dto.setPost_title(title);
		dto.setPost_content(content);

		try {
			dto.setTarget_type(TargetType.valueOf(targetType));
		} catch (IllegalArgumentException e) {
			model.addAttribute("msg", "잘못된 게시판 유형입니다.");
			return "community/write_view";
		}
		dto.setTarget_id(1);

		write_viewDao.insertPost(dto);
		Long postId = dto.getPost_id();

		// 이미지들을 file 테이블에 저장
		for (MultipartFile file : imgFiles) {
			if (!file.isEmpty()) {
				try {
					String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
					File uploadPath = new File(uploadDir);
					if (!uploadPath.exists())
						uploadPath.mkdirs();

					File destFile = new File(uploadDir + saveName);
					file.transferTo(destFile);

					// file 테이블에 insert
					FileDto fileDto = new FileDto();
					fileDto.setFile_name(saveName);
					fileDto.setFile_path("/images/" + saveName); // URL 경로
					fileDto.setTarget_type(TargetType.valueOf(targetType));
					fileDto.setTarget_id(postId);

					fileDao.insertFile(fileDto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return "redirect:/community/review_view";
	}
}
