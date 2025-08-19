package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService implements JoinServiceInter {

	private final JoinDao joinDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SaveUserActivityLog userActivityLog;

	@Override
	public void join(JoinformDto joinformDto, Model model) {
		
		// 비밀번호 암호화
		String encodedPw = bCryptPasswordEncoder.encode(joinformDto.getPw());
		joinformDto.setPw(encodedPw);

		joinDao.insertUserDetail(joinformDto);  // user_detail 테이블 insert

		joinDao.insertUserLogin(joinformDto);  // user_login 테이블 정보입력

		// 로그 남기는 메서드입니다.. 여기 안쓰면 로그를 남길 수 없어요...
		Long userId = joinformDto.getUserId();
		String loginId = joinformDto.getLoginId();
		if (userId != null && loginId != null) {
			userActivityLog.createAndSaveLog(
					userId,
					"USER_JOIN",
					"USER",
					userId,
					"User joined with ID: " + loginId
			);
		}

	}
	

}
