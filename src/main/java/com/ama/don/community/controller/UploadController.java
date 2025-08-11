package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;

@Controller
@RequestMapping("/file")
public class UploadController {

	String uploadPath = "C:/upload/";

	@Autowired
	private FileDao fileDao;

	@PostMapping("/upload_image")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("target_type") String targetTypeStr, @RequestParam("target_id") Long targetId)
			throws IOException {

		Long userId = 1L;

		// 실제 저장할 파일 이름
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File saveFile = new File(uploadPath + saveName);
		file.transferTo(saveFile);

		// DB 저장용 DTO 생성
		FileDto fileDto = new FileDto();
		fileDto.setFile_uploader(userId.toString());
		fileDto.setTarget_type(TargetType.valueOf(targetTypeStr));
		fileDto.setFile_name(saveName);
		fileDto.setFile_path("/upload/" + saveName);
		fileDto.setTarget_id(targetId);
		System.out.println("파일 업로드 시@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ target_id: " + fileDto.getTarget_id());

		System.out.println("INSERT @@@@@@@@@@@@@@@@@@@@@@@@@@@@" + "@@@@@@@@@@@@전 DTO 상태: " + fileDto);
		fileDao.create(fileDto);

		return "success";
	}

	@PostMapping("/delete_temp")
	@ResponseBody
	public void deleteTempFiles(@RequestParam("user_id") Long userId,
			@RequestParam("target_type") String targetTypeStr) {
		TargetType targetType = TargetType.valueOf(targetTypeStr);
		fileDao.deleteTempFiles(targetType, userId);
	}

}
