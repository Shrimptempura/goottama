package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;

@Controller
@RequestMapping("/community")
public class Write_viewController {

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	private final String uploadDir = "C:/upload/";

	@GetMapping("/write_view")
	public String writeForm() {
	    return "community/write_view";
	}


	@PostMapping("/write")
	public String writePost(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("targetType") String targetType,
			@RequestParam(value = "imgFiles", required = false) MultipartFile[] imgFiles, Model model) {

		PostDto dto = new PostDto();
		dto.setUser_id(1L); // 임시 값
		dto.setPost_title(title);
		dto.setPost_content(content);

		TargetType type;
		try {
			type = TargetType.valueOf(targetType);
			dto.setTargetType(type);
		} catch (IllegalArgumentException e) {
			model.addAttribute("msg", "잘못된 게시판 유형입니다.");
			return "community/write_view";
		}

		dto.setTargetId(1L); // 임시 값
		postDao.create(dto);
		Long postId = dto.getPost_id();

		// 업로드 디렉토리 생성
		File uploadPath = new File(uploadDir);
		if (!uploadPath.exists())
			uploadPath.mkdirs();

		if (imgFiles != null) {
			for (MultipartFile file : imgFiles) {
				if (!file.isEmpty()) {
					try {
						String orgName = file.getOriginalFilename();
						String ext = orgName.substring(orgName.lastIndexOf('.') + 1);
						String saveName = UUID.randomUUID().toString().replace("-", "") + "." + ext;

						File destFile = new File(uploadDir + saveName);
						file.transferTo(destFile);

						FileDto fileDto = new FileDto();
						fileDto.setFile_name(orgName);
						fileDto.setFile_path(saveName);
						fileDto.setFile_uploader(type.name());
						fileDto.setTarget_type(type);
						fileDto.setTarget_id(postId);

						fileDao.create(fileDto);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return "redirect:/community/review_view";
	}
}
