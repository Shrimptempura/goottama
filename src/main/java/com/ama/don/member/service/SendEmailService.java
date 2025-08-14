package com.ama.don.member.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.config.EmailConfig;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.dto.MemberDto;
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
		
		String subject = "아마겟돈 회원가입 인증을 위한 메일입니다.";
		String link = emailConfig.getCallbackUrl()+"emailCheck?code="+code+"&loginId="+loginId;
		String content=
				 "<div style=\"font-family: Arial, sans-serif; font-size: 16px; color: #333;\">" +
					        "<p>안녕하세요,</p>" +
					        "<p>아래 버튼을 클릭하여 이메일 인증을 완료해주세요.</p>" +

					        "<div style=\"margin: 20px 0;\">" +
					            "<a href=\"" + link + "\" " +
					               "style=\"display: inline-block; " +
					                       "background-color: #007bff; " +
					                       "color: white; " +
					                       "padding: 12px 24px; " +
					                       "text-align: center; " +
					                       "text-decoration: none; " +
					                       "font-weight: bold; " +
					                       "border-radius: 6px; " +
					                       "font-size: 16px;\">" +
					                "이메일 인증하기" +
					            "</a>" +
					        "</div>" +
					    "</div>";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content,true);
			helper.setFrom(emailConfig.getFrom(),"아마겟돈");
			
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
			helper.setFrom(emailConfig.getFrom(),"아마겟돈");
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void sendInquiryEmail(MemberDto memberDto,String subject, String message) {
		
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
			String email = memberDto.getUser_email();
			
			String content = "<div>"
				    + "<p><strong>보낸 사람 이메일:</strong> " + email + "</p>"
				    + "<p><strong>내용:</strong><br/>" + message + "</p>"
				    + "</div>";
			
			helper.setTo(emailConfig.getFrom());
			helper.setSubject(subject);
			helper.setText(content,true);
			helper.setFrom(email);
			helper.setReplyTo(email);
			
			mailSender.send(mail);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
