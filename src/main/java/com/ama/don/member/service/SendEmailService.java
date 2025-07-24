package com.ama.don.member.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.utill.EmailSHA;
import com.ama.don.member.utill.Gmail;

@Service
public class SendEmailService implements SendEmailServiceInter{

	@Override
	public void emailSendAction(JoinformDto joinformDto, Model model) {
	
		String host="http://localhost:8505/";
		String from="hj4530hj271@gmail.com";
		
		String loginId = joinformDto.getLoginId();
		String to = joinformDto.getEmail();
		String code = new EmailSHA().getSHA256(to);
		String subject="회원가입 인증을 위한 메일입니다.";
		String content="다음 링크 클릭 이메일 인증을 진행하세요."+"<a href='"+host+"emailCheck?code="+code+"&loginId="+loginId+"'><b>이메일 인증하기</b></a>";
		
		Properties p=new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "465");
		
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		try{
			Authenticator auth = new Gmail();
			Session ses=Session.getInstance(p,auth);
			ses.setDebug(true);
			MimeMessage msg=new MimeMessage(ses);
			msg.setSubject(subject);
			Address fromAddr=new InternetAddress(from);
			msg.setFrom(fromAddr);
			Address toAddr=new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html;charset=UTF-8");
			Transport.send(msg);
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
