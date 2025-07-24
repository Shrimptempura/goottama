package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.community.dao.Write_viewDao;
import com.ama.don.community.dto.Write_viewDto;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class Write_viewController {

	@Autowired
	private Write_viewDao write_viewDao;

	private final String uploadDir = "C:/upload/";

	@GetMapping("/Community/write_view")
	public String writeForm() {
		return "community/write_view";
	}

	@PostMapping("/write")
	public String writePost(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("imgFile") MultipartFile imgFile, Model model) {

		Write_viewDto dto = new Write_viewDto();
		dto.setTitle(title);
		dto.setContent(content);

		if (!imgFile.isEmpty()) {
			String originalFilename = imgFile.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + "_" + originalFilename;

			try {
				File saveFile = new File(uploadDir + saveFileName);
				imgFile.transferTo(saveFile);
				dto.setImg(saveFileName);
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("msg", "이미지 업로드 실패");
				return "community/write_view";
			}
		}

		write_viewDao.insertPost(dto);

		return "redirect:/community/review_view";
	}
}
