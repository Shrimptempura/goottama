package com.ama.don.community.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.member.dao.LoginDao;

@Controller
@RequestMapping("/file")
public class UploadController {

	String uploadPath = "C:/upload/";

	@Autowired
	private FileDao fileDao;
	@Autowired
	private LoginDao loginDao;

	// 이미지 업로드
	@PostMapping("/upload_image")
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("target_type") String targetTypeStr,
			@RequestParam(value = "target_id", required = false) Long targetId,
			@RequestParam(value = "temp_id", required = false) Long tempId) throws IOException {

		// 로그인한 사용자에서 user_id 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
			throw new SecurityException("로그인이 필요합니다.");
		}
		String loginId = auth.getName();
		Long userId = loginDao.findUserIdByLoginId(loginId);
		if (userId == null) {
			throw new IllegalStateException("사용자 정보를 찾을 수 없습니다.");
		}

		// 서버에 파일 저장
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File saveFile = new File(uploadPath + saveName);
		file.transferTo(saveFile);

		// target_id 결정
		Long effectiveTargetId;
		if (targetId != null) {
			effectiveTargetId = targetId; // 확정 업로드
		} else if (tempId != null) {
			effectiveTargetId = tempId; // 프론트 임시 ID
		} else {
			effectiveTargetId = -System.currentTimeMillis(); // 서버 임시 ID
		}

		// DB 저장
		FileDto fileDto = new FileDto();
		fileDto.setFile_uploader(userId.toString());
		fileDto.setTarget_type(TargetType.valueOf(targetTypeStr));
		fileDto.setFile_name(saveName);
		fileDto.setFile_path("/upload/" + saveName);
		fileDto.setTarget_id(effectiveTargetId);

		fileDao.create(fileDto);

		return effectiveTargetId.toString();
	}

	// 임시 파일 삭제
	@PostMapping("/delete_temp")
	@ResponseBody
	public void deleteTempFiles(@RequestParam("target_type") String targetTypeStr,
			@RequestParam(value = "keep_temp_id", required = false) Long keepTempId) {
		Long userId = getCurrentUserId();
		TargetType targetType = TargetType.valueOf(targetTypeStr);
		if (keepTempId == null) {
			fileDao.deleteTempFiles(targetType, userId);
		} else {
			fileDao.deleteTempFilesExcept(targetType, userId, keepTempId);
		}
	}

	// 임시 → 실제 ID 업데이트
	@PostMapping("/confirm")
	@ResponseBody
	public String confirmFiles(@RequestParam("target_type") String targetTypeStr, @RequestParam("temp_id") Long tempId,
			@RequestParam("target_id") Long targetId) {
		Long userId = getCurrentUserId();
		fileDao.updateTargetId(TargetType.valueOf(targetTypeStr), String.valueOf(userId), tempId, targetId);
		return "ok";
	}

	// 공통 로그인 사용자 user_id 조회 메서드
	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
			throw new SecurityException("로그인이 필요합니다.");
		}
		String loginId = auth.getName();
		Long userId = loginDao.findUserIdByLoginId(loginId);
		if (userId == null) {
			throw new IllegalStateException("사용자 정보를 찾을 수 없습니다.");
		}
		return userId;
	}
}
