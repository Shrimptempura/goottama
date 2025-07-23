package com.ama.don.admin.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 웹 관련 설정을 담당하는 구성 클래스.<br/>
 * 이 설정은 Spring Boot에게 file.upload-location에 지정된 실제 디스크 경로를 웹에서 특정 URL 패턴으로 접근할 수 있도록 매핑하라고 알려줌 <br/>
 * 정적 자원 핸들러 추가 및 애플리케이션 시작 시 필요한 디렉토리 생성을 처리함.<br/>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // application.properties에서 설정한 업로드 경로를 주입받음
    @Value("${file.upload-location}")
    private String uploadLocation;
    // 첨부파일용 경로 주입
    @Value("${file.attachment-upload-location}")
    private String attachmentUploadLocation;

    /**
     * 애플리케이션 시작 시 디렉토리가 없으면 생성함
     */
    @PostConstruct
    public void init() {
        // TUI 에디터 이미지 업로드 디렉토리 생성
        File imageDirectory = new File(uploadLocation);
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }
        // 첨부파일 업로드 디렉토리 생성
        File attachmentDirectory = new File(attachmentUploadLocation);
        if (!attachmentDirectory.exists()) {
            attachmentDirectory.mkdirs();
        }
    }

    /**
     * 웹 요청 경로를 실제 파일 시스템의 자원 경로로 매핑하는 핸들러 추가함.<br/>
     * `/uploadedImages/**` 패턴 요청을 TUI 에디터 이미지 저장 경로에 연결하고,<br/>
     * `/attachments/**` 패턴 요청을 일반 첨부파일 저장 경로에 연결하여 웹에서 파일 접근 가능하게 함.<br/>
     * 'file:' 접두사를 사용하여 파일 시스템 경로임을 명시함.
     *
     * @param registry 정적 자원 핸들러를 등록하기 위한 {@link org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry} 객체.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadedImages/**") // 웹에서 접근할 URL 패턴
                .addResourceLocations("file:" + uploadLocation); // 실제 파일 시스템 경로

        // 첨부파일을 웹으로 노출시키는 URL 매핑
        registry.addResourceHandler("/attachments/**") // 웹에서 접근할 URL 패턴
                .addResourceLocations("file:" + attachmentUploadLocation); // 실제 파일 시스템 경로

        // 기존 static 리소스 유지
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}