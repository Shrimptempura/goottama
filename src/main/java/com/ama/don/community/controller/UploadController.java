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

	// 이미지 업로드 - target_id가 넘어오면 그대로 저장 - target_id가 없고 temp_id가 오면
	// temp_id(음수)를 target_id로 저장(임시 업로드) - 둘 다 없으면 서버에서 음수 temp_id 생성하여 저장

	@PostMapping("/upload_image")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("target_type") String targetTypeStr,
			@RequestParam(value = "target_id", required = false) Long targetId,
			@RequestParam(value = "temp_id", required = false) Long tempId,
			@RequestParam(value = "user_id", required = false) Long userIdParam) throws IOException {

		Long userId = (userIdParam != null) ? userIdParam : 1L;

		// 서버에서 파일 저장
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File saveFile = new File(uploadPath + saveName);
		file.transferTo(saveFile);

		// 임시ID 음수
		Long effectiveTargetId;
		if (targetId != null) {
			effectiveTargetId = targetId; // 확정 업로드
		} else if (tempId != null) {
			effectiveTargetId = tempId; // 프론트가 생성한 임시 음수ID
		} else {
			effectiveTargetId = -System.currentTimeMillis(); // 서버가 생성한 임시 음수ID
		}

		// DTO
		FileDto fileDto = new FileDto();
		fileDto.setFile_uploader(userId.toString());
		fileDto.setTarget_type(TargetType.valueOf(targetTypeStr));
		fileDto.setFile_name(saveName);
		fileDto.setFile_path("/upload/" + saveName);
		fileDto.setTarget_id(effectiveTargetId);

		fileDao.create(fileDto);

		return effectiveTargetId.toString();
	}

	// 같은 사용자/타입의 "이전 음수 파일" 정리 현재 세션에 쓰는 temp_id는 제외하고 지우고 싶으면 keep_temp_id
	// 전달 - keep_temp_id 없으면 음수 전부 삭제

	@PostMapping("/delete_temp")
	@ResponseBody
	public void deleteTempFiles(@RequestParam("user_id") Long userId, @RequestParam("target_type") String targetTypeStr,
			@RequestParam(value = "keep_temp_id", required = false) Long keepTempId) {
		TargetType targetType = TargetType.valueOf(targetTypeStr);
		if (keepTempId == null) {
			fileDao.deleteTempFiles(targetType, userId);
		} else {
			fileDao.deleteTempFilesExcept(targetType, userId, keepTempId);
		}
	}

	// 임시 target_id → 실제 target_id로 일괄 업데이트
	@PostMapping("/confirm")
	@ResponseBody
	public String confirmFiles(@RequestParam("target_type") String targetTypeStr, @RequestParam("user_id") Long userId,
			@RequestParam("temp_id") Long tempId, @RequestParam("target_id") Long targetId) {
		fileDao.updateTargetId(TargetType.valueOf(targetTypeStr), String.valueOf(userId), tempId, targetId);
		return "ok";
	}
}
