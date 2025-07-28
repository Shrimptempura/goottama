package com.ama.don.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class EmailConfig {

	@Value("${spring.mail.username}")
	private String from;
	
	@Value("${custom.callback-url}")
	private String callbackUrl;

}
