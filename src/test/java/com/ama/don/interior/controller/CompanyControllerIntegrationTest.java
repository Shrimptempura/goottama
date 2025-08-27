package com.ama.don.interior.controller;

import com.ama.don.interior.service.CompanyAuthService;
import com.ama.don.interior.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
@AutoConfigureMockMvc
@SpringBootTest
class CompanyControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CompanyAuthService companyAuthService;

    @MockitoBean
    FileService fileService;

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
    @Sql(scripts = {"/sql/schema.sql", "/sql/seed_user_901.sql"})
    @Test
    void create_success() throws Exception {
        when(companyAuthService.getLoginUserId()).thenReturn(901L);
        doNothing().when(fileService).saveFile(any(), anyLong(), any(), anyBoolean());

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        mvc.perform(multipart("/interior/new-company")
                .file(file)
                .param("detail.companyName", "테스트업체")
                .param("location.address", "서울시 구로구")
                .with(user("companyIT002").roles("MEMBER"))
                .with(csrf())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/interior/ihome"));
    }




}