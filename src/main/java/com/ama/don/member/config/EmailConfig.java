package com.ama.don.member.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class EmailConfig {
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String from;
	private String callbackUrl;

}
