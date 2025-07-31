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
		
		String login_id = memberDto.getLogin_id();
		long user_id = memberDto.getUser_id();
		String uploadDir = "C:\\upload\\profile\\"; //저장디렉토리
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdir(); //디렉토리가 없으면 생성
		}
		
		//프로필 이미지를 삭제학경우 기본이미지
		
		
		//선택한 파일이 없으면 리턴
		if (file == null || file.isEmpty()) {
			return;
		}
		//기존 프로필사진이 있으면 기존사진 삭제
		String oldFileName = memberDto.getUser_img();
		if (oldFileName != null && !oldFileName.isEmpty()) {
			File oldFile = new File(uploadDir+oldFileName);
			if (oldFile.exists()) {
				oldFile.delete();
			}
			//db 데이터 삭제(file테이블,member테이블)
			fileDao.deleteProfileImg(oldFileName); //user_detail테이블 데이터 삭제
			fileDao.deleteByTargetAndUploader(login_id,TargetType.MEMBER,1);		
		}
		
		//프로필 사진 변경	
		String originalName = file.getOriginalFilename();				
		String uuidName =UUID.randomUUID().toString()+originalName; //고유파일명 생성
		String fullPath = uploadDir + uuidName;
		
		file.transferTo(new File(fullPath)); //파일 저장
		
		memberDto.setUser_img(uuidName);
		
		//db에 파일 저장
		FileDto fileDto = new FileDto();
		fileDto.setFile_uploader(login_id);
		fileDto.setFile_name(uuidName);
		fileDto.setFile_path(fullPath);
		fileDto.setTarget_type(TargetType.MEMBER);
		fileDto.setTarget_id(1);
		
		fileDao.create(fileDto);
		fileDao.createProfileImg(uuidName,user_id); //여기 점검,기능 확인
		
		
	}

}
