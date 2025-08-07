package com.ama.don.member.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.config.EmailConfig;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.utill.EmailSHA;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendEmailService implements SendEmailServiceInter{
	
	private final EmailConfig emailConfig;
	private final JavaMailSender mailSender;
	
	@Override
	public void emailSendAction(JoinformDto joinformDto, Model model) {
		
		String loginId = joinformDto.getLoginId();
		String to = joinformDto.getEmail();
		String code = new EmailSHA().getSHA256(to);
		
		String subject = "회원가입 인증을 위한 메일입니다.";
		String link = emailConfig.getCallbackUrl()+"emailCheck?code="+code+"&loginId="+loginId;
		String content="다음 링크 클릭 이메일 인증을 진행하세요. " + "<a href=\"" + link + "\"><b>이메일 인증하기</b></a>";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content,true);
			helper.setFrom(emailConfig.getFrom());
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void sendPwcodeEmailAction(FindPwDto findPwDto, String code) {

		String to = findPwDto.getEmail();		
		String subject = "아마겟돈 비밀번호 재설정 인증 코드";
		String content = "<div style='font-family: Arial, sans-serif; font-size: 14px;'>"
		        + "<p><strong>아마겟돈 비밀번호 재설정 인증 코드:</strong> <span style='color: blue; font-weight: bold;'>" + code + "</span></p>"
		        + "<p>사이트로 돌아가셔서 위의 인증코드를 입력해주세요.</p>"
		        + "</div>";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content,true);
			helper.setFrom(emailConfig.getFrom());
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
