package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class UploadController {

	@PostMapping("/upload")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File destFile = new File("C:/upload/" + saveName);
		file.transferTo(destFile);
		return "/images/" + saveName;
	}
}
