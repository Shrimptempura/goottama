package com.ama.don.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @deprecated security config의 도입으로 사용 중지
 */
//@Configuration
public class LoginWebConfig  implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LoginCheckInterceptor())
			.addPathPatterns("/mypage/**")
			.excludePathPatterns("/login_view","/logout");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
			
		registry.addResourceHandler("/profile/**")
				.addResourceLocations("file:///C:/member/profile/");
	}

}
