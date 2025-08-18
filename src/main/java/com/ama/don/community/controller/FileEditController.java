package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.member.dao.LoginDao;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileEditController {

	@Autowired
	private FileDao fileDao;
	@Autowired
	private LoginDao loginDao;

	private static final String UPLOAD_PATH = "C:/upload/";

	@PostMapping(path = "/upload_image_final", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public Map<String, Object> uploadImageFinal(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "targetType", required = false) TargetType targetType,
			@RequestParam(value = "targetId", required = false) Long targetId,
			@RequestParam(value = "target_type", required = false) String targetTypeLegacy,
			@RequestParam(value = "target_id", required = false) Long targetIdLegacy) throws IOException {

		// targetType/Id 레거시 파라미터 보정
		if (targetType == null && targetTypeLegacy != null) {
			targetType = TargetType.valueOf(targetTypeLegacy);
		}
		if (targetId == null) {
			targetId = targetIdLegacy;
		}
		if (targetType == null || targetId == null) {
			return Map.of("success", false, "message", "missing target");
		}

		// ✅ 로그인 사용자 login_id → user_id 조회 (member 코드 수정 불필요)
		Long userId = getCurrentUserId();
		if (userId == null) {
			return Map.of("success", false, "message", "unauthorized");
		}

		// 파일 저장
		String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File saveFile = new File(UPLOAD_PATH + saveName);
		file.transferTo(saveFile);

		// DB 저장
		FileDto fileDto = new FileDto();
		fileDto.setFile_uploader(String.valueOf(userId));
		fileDto.setTarget_type(targetType);
		fileDto.setTarget_id(targetId);
		fileDto.setFile_name(saveName);
		fileDto.setFile_path("/upload/" + saveName);

		fileDao.create(fileDto);

		return Map.of("success", true, "file_id", fileDto.getFile_id(), "file_path", fileDto.getFile_path());
	}

	// 공통: 현재 로그인 사용자 user_id 조회
	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
			return null;
		}
		String loginId = auth.getName(); // principal 타입과 무관하게 username(login_id) 반환
		return loginDao.findUserIdByLoginId(loginId);
	}
}
