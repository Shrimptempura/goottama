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

	private final String uploadPath = "C:/upload/";

	@Autowired
	private FileDao fileDao;

	@PostMapping("/upload")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("targetType") TargetType targetType) throws IOException {
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File destFile = new File("C:/upload/" + saveName);
		file.transferTo(destFile);

		FileDto fileDto = new FileDto();
		fileDto.setFile_name(file.getOriginalFilename());
		fileDto.setFile_path("/uploadedImages/" + saveName);
		fileDto.setFile_uploader("community");
		fileDto.setTarget_type(targetType);
		fileDto.setTarget_id(1L); 

		fileDao.create(fileDto);

		return fileDto.getFile_path();
	}

}
