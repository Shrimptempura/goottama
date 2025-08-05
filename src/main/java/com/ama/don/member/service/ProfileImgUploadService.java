package com.ama.don.member.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileImgUploadService {

	private final FileDao fileDao;

	@Transactional
	public void changeProfileImg(MemberDto memberDto, MultipartFile file) throws IllegalStateException, IOException {

		String login_id = memberDto.getLogin_id();
		long user_id = memberDto.getUser_id();
		String uploadDir = "C:\\member\\profile\\"; // 저장디렉토리
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs(); // 디렉토리가 없으면 생성
		}

		// 프로필 이미지를 삭제학경우 기본이미지

		// 선택한 파일이 없으면 리턴
		if (file == null || file.isEmpty()) {
			return;
		}
		// 기존 프로필사진이 있으면 기존사진 삭제
		String oldFileName = memberDto.getUser_img();
		if (oldFileName != null && !oldFileName.isEmpty()) {
			File oldFile = new File(uploadDir + oldFileName);
			if (oldFile.exists()) {
				oldFile.delete();
			}
			// db 데이터 삭제(file테이블,member테이블)
			fileDao.deleteByTargetAndUploader(login_id, TargetType.MEMBER, user_id); // file테이블 삭제
			fileDao.deleteProfileImg(oldFileName); // user_detail테이블 데이터 삭제
		}

		// 프로필 사진 변경 //파일 저장
		String originalName = file.getOriginalFilename();
		String uuidName = UUID.randomUUID().toString() + originalName; // 고유파일명 생성
		String fullPath = uploadDir + uuidName;

		file.transferTo(new File(fullPath));

		memberDto.setUser_img(uuidName);

		// db저장 예외 발생 시 디렉토리에 이미 저장된 파일 삭제
		try {
			// dto에 주입
			FileDto fileDto = new FileDto();
			fileDto.setFile_uploader(login_id);
			fileDto.setFile_name(uuidName);
			fileDto.setFile_path(fullPath);
			fileDto.setTarget_type(TargetType.MEMBER);
			fileDto.setTarget_id(user_id);
			// db 저장
			fileDao.create(fileDto);
			fileDao.createProfileImg(uuidName, user_id);

		} catch (Exception e) {
			File saveFile = new File(fullPath);
			if (saveFile.exists()) {
				saveFile.delete();
			}
			throw e;
		}

	}

}
