package com.ama.don.member.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.config.EmailConfig;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.utill.EmailSHA;

@Service
public class SendEmailService implements SendEmailServiceInter{
	
	private final EmailConfig emailConfig;
	
	@Autowired
	public SendEmailService(EmailConfig emailConfig) {
		this.emailConfig = emailConfig;
	}

	@Override
	public void emailSendAction(JoinformDto joinformDto, Model model) {
		
		String loginId = joinformDto.getLoginId();
		String to = joinformDto.getEmail();
		String code = new EmailSHA().getSHA256(to);
		
		String subject = "회원가입 인증을 위한 메일입니다.";
		String link = emailConfig.getCallbackUrl()+"emailCheck?code="+code+"&loginId="+loginId;
		String content="다음 링크 클릭 이메일 인증을 진행하세요. " + "'<a href='" + link + "'><b>이메일 인증하기</b></a>";
		
		Properties p=new Properties();
		p.put("mail.smtp.host", emailConfig.getHost());
		p.put("mail.smtp.port", emailConfig.getPort());
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.socketFactory.port", String.valueOf(emailConfig.getPort()));
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		try{
			Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
			}
			};
			
			Session ses=Session.getInstance(p,auth);
			ses.setDebug(true);
			
			MimeMessage msg=new MimeMessage(ses);
			msg.setFrom(new InternetAddress(emailConfig.getFrom()));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));			
			msg.setSubject(subject);
			msg.setContent(content, "text/html;charset=UTF-8");
			
			Transport.send(msg);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
