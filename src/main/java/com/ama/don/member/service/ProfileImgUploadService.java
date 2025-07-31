package com.ama.don.member.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
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

	public void changeProfileImg(MemberDto memberDto, MultipartFile file) throws IllegalStateException, IOException {
		
		//프로필 이미지를 삭제학경우 기본이미지
		//선택한 파일이 없을때
		
		//프로필 사진 변경
		String uploadDir = "C:\\upload\\profile\\"; //저장디렉토리
		File dir = new File(uploadDir);
		
		String login_id = memberDto.getLogin_id();
		String originalName = file.getOriginalFilename();		
		long user_id = memberDto.getUser_id();
		String uuidName = user_id + UUID.randomUUID().toString(); //고유파일명 생성
		String fullPath = uploadDir + uuidName;
		
		file.transferTo(new File(fullPath)); //파일 저장
		
		memberDto.setUser_img(uuidName);
		
		//db에 파일 저장
		FileDto fileDto = new FileDto();
		fileDto.setFile_uploader(login_id);
		fileDto.setFile_name(uuidName);
		fileDto.setFile_path(fullPath);
		fileDto.setTarget_type(TargetType.MEMBER);
		fileDto.setTarget_id(user_id);
		
		fileDao.create(fileDto);
		
		
	}

}
