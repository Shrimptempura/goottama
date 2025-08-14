package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.common.dao.FileDao;

@Controller
@RequestMapping("/file")
public class FileEditController {

	@Autowired
	private FileDao fileDao;

	@PostMapping(path = "/upload_image_final", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public java.util.Map<String, Object> uploadImageFinal(
			@RequestParam("file") org.springframework.web.multipart.MultipartFile file,
			@RequestParam(value = "targetType", required = false) com.ama.don.common.enums.TargetType targetType,
			@RequestParam(value = "targetId", required = false) Long targetId,
			@RequestParam(value = "target_type", required = false) String targetTypeLegacy,
			@RequestParam(value = "target_id", required = false) Long targetIdLegacy,
			@RequestParam(value = "user_id", required = false) Long userIdParam) throws java.io.IOException {

		if (targetType == null && targetTypeLegacy != null) {
			targetType = com.ama.don.common.enums.TargetType.valueOf(targetTypeLegacy);
		}
		if (targetId == null) {
			targetId = targetIdLegacy;
		}
		if (targetType == null || targetId == null) {
			return java.util.Map.of("success", false, "message", "missing target");
		}

		Long userId = (userIdParam != null) ? userIdParam : 1L;

		String uploadPath = "C:/upload/"; // 기존과 동일 경로 사용
		String saveName = java.util.UUID.randomUUID() + "_" + file.getOriginalFilename();
		java.io.File saveFile = new java.io.File(uploadPath + saveName);
		file.transferTo(saveFile);

		com.ama.don.common.dto.FileDto fileDto = new com.ama.don.common.dto.FileDto();
		fileDto.setFile_uploader(String.valueOf(userId));
		fileDto.setTarget_type(targetType);
		fileDto.setTarget_id(targetId);
		fileDto.setFile_name(saveName);
		fileDto.setFile_path("/upload/" + saveName);

		fileDao.create(fileDto);

		return java.util.Map.of("success", true, "file_id", fileDto.getFile_id(), "file_path", fileDto.getFile_path());
	}

}
