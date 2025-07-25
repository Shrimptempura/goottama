package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dao.Write_viewDao;
import com.ama.don.community.dto.Write_viewDto;

@Controller
@RequestMapping("/community")
public class Write_viewController {

	@Autowired
	private Write_viewDao write_viewDao;

	private final String uploadDir = "C:/upload/";

	@GetMapping("/write_view")
	public String writeForm() {
		return "community/write_view";
	}

	@PostMapping("/upload-image")
	@ResponseBody
	public String uploadImage(@RequestParam("imgFile") MultipartFile file) throws IOException {
		String uploadDir = "C:/upload/";
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		file.transferTo(new File(uploadDir + saveName));
		return "/images/" + saveName;
	}

	@PostMapping("/write")
	public String writePost(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("imgFile") MultipartFile imgFile, Model model) {

		Write_viewDto dto = new Write_viewDto();
		dto.setUser_id(1); // 실제 사용 시: 로그인 유저 ID 세션에서 받아오기
		dto.setPost_title(title);
		dto.setPost_content(content);
		dto.setTarget_type(TargetType.valueOf("COMMUNITY"));
		dto.setTarget_id(1);

		if (!imgFile.isEmpty()) {
			String originalFilename = imgFile.getOriginalFilename();
			String saveFileName = UUID.randomUUID() + "_" + originalFilename;

			try {
				File saveFile = new File(uploadDir + saveFileName);
				imgFile.transferTo(saveFile);
				dto.setPost_img(saveFileName);
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
