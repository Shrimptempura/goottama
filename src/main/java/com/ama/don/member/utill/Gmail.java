package com.ama.don.member.utill;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator{
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("hj4530hj271@gmail.com","wvxv ksnp huas snwf");
		
	}
}

