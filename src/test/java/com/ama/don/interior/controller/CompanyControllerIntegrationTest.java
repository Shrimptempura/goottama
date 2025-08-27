package com.ama.don.interior.controller;

import com.ama.don.common.service.ReviewService;
import com.ama.don.interior.service.CompanyAuthService;
import com.ama.don.interior.service.CompanyPostService;
import com.ama.don.interior.service.CompanyReviewService;
import com.ama.don.interior.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = CompanyControllerIntegrationTest.TestExcludeAdmin.class)
class CompanyControllerIntegrationTest {

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(
            basePackages = {
                    "com.ama.don.interior"
            },
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.ama\\.don\\.admin\\..*"),
                    @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.ama\\.don\\.common\\.config\\..*")
            }
    )
    @org.mybatis.spring.annotation.MapperScan({
            "com.ama.don.interior.**.dao"
//            "com.ama.don.member.**.dao"
    })
    static class TestExcludeAdmin {
    }

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CompanyAuthService companyAuthService;

    @MockitoBean
    FileService fileService;

    @MockitoBean
    ReviewService reviewService;

    @MockitoBean
    CompanyPostService companyPostService;

    @MockitoBean
    CompanyReviewService companyReviewService;

    @Container
    static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:11.4");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDBContainer::getUsername);
        registry.add("spring.datasource.password", mariaDBContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.mariadb.jdbc.Driver");
    }

    @DisplayName("업체 등록 성공 -> redirect:/interior/ihome")
    @Sql(scripts = "/sql/seed_user_901.sql")
    @Test
    void create_success() throws Exception {
        when(companyAuthService.getLoginUserId()).thenReturn(901L);
        doNothing().when(fileService).saveFile(any(), anyLong(), any(), anyBoolean());

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        mvc.perform(multipart("/interior/new-company")
                        .file(file)
                        .param("companyName", "테스트업체")
                        .param("companyAddr", "테스트주소")
                        .param("companyField", "테스트필드")
                        .param("companyLicense", "테스트라이센스")
                        .param("companyAs", "테스트AS")
                        .param("companyCareer", "테스트경력")
                        .param("companyIntro", "테스트소개")
                        .param("locationAddr", "서울시 구로구")
                        .characterEncoding("UTF-8")
                        .with(user("companyIT002").roles("MEMBER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/interior/ihome"));
    }


}