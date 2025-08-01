package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;

@Controller
@RequestMapping("/file")
public class UploadController {

	private final String uploadPath = "C:/upload/";

	@Autowired
	private FileDao fileDao;

	@PostMapping("/upload_image")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("target_type") String targetTypeStr, @RequestParam("post_id") Long postId)
			throws IOException {

		System.out.println("전달받은 targetType: " + targetTypeStr);

		// 실제 저장할 파일 이름
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File saveFile = new File(uploadPath + saveName);
		file.transferTo(saveFile);

		// DB 저장용 DTO 생성
		FileDto fileDto = new FileDto();
		fileDto.setFile_name(file.getOriginalFilename());
		fileDto.setFile_path("/uploaded_images/" + saveName);
		fileDto.setFile_uploader("테스트유저");
		fileDto.setTarget_id(postId);

		try {
			TargetType targetType = TargetType.valueOf(targetTypeStr);
			fileDto.setTarget_type(targetType);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return "invalid_target_type";
		}

		// DB 저장
		fileDao.create(fileDto);

		return "/uploaded_images/" + saveName;
	}
}
