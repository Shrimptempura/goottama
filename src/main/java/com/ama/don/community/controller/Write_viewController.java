package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dao.Write_viewDao;
import com.ama.don.community.dto.Write_viewDto;

@Controller
@RequestMapping("/community")
public class Write_viewController {

	@Autowired
	private Write_viewDao write_viewDao;

	private final String uploadDir = "C:/upload/";

	// 글쓰기 페이지
	@GetMapping("/write_view")
	public String writeForm() {
		return "community/write_view";
	}

	@PostMapping("/write")
	public String writePost(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("targetType") String targetType, @RequestParam("imgFile") MultipartFile imgFile,
			Model model) {

		Write_viewDto dto = new Write_viewDto();
		dto.setUser_id(1);
		dto.setPost_title(title);
		dto.setPost_content(content);

		// 타겟 타입 설정
		try {
			dto.setTarget_type(TargetType.valueOf(targetType));
		} catch (IllegalArgumentException e) {
			model.addAttribute("msg", "잘못된 게시판 유형입니다.");
			return "community/write_view";
		}
		dto.setTarget_id(1);

		// 이미지 업로드
		if (!imgFile.isEmpty()) {
			String saveFileName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();
			File dir = new File(uploadDir);
			if (!dir.exists())
				dir.mkdirs();

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

	// 드래그앤드롭 이미지 업로드 처리
	@PostMapping("/write_view")
	@ResponseBody
	public String dragAndDropUpload(MultipartHttpServletRequest multipartRequest) {
		Iterator<String> itr = multipartRequest.getFileNames();

		while (itr.hasNext()) {
			MultipartFile mpf = multipartRequest.getFile(itr.next());

			if (mpf != null && !mpf.isEmpty()) {
				String originalFilename = mpf.getOriginalFilename();
				String saveName = UUID.randomUUID() + "_" + originalFilename;
				String fileFullPath = uploadDir + saveName;

				try {
					File dir = new File(uploadDir);
					if (!dir.exists())
						dir.mkdirs();

					mpf.transferTo(new File(fileFullPath));
					System.out.println("업로드 성공: " + fileFullPath);

					return "/upload/" + saveName;

				} catch (Exception e) {
					System.out.println("파일 업로드 실패 => " + fileFullPath);
					e.printStackTrace();
				}
			}
		}

		return "fail";
	}
}
