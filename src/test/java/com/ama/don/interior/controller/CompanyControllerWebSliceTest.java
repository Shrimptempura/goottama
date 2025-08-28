package com.ama.don.interior.controller;

import com.ama.don.common.service.ReviewService;
import com.ama.don.interior.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CompanyController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CompanyController.class)
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        CompanyCommentController.class,
                        CompanyPostController.class
                })
        }
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CompanyControllerWebSliceTest {

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

    @MockitoBean
    CompanyService companyService;

    @DisplayName("업체 등록 폼 제출시 -> redirect:/interior/ihome")
    @Test
    void create_success_on_redirect() throws Exception {
        doNothing().when(companyService).createCompany(any(), any(), any());

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        mvc.perform(multipart("/interior/new-company")
                        .file(file)
                        .param("detail.companyName", "테스트업체")
                        .param("detail.companyAddr", "테스트주소")
                        .param("detail.companyField", "테스트필드")
                        .param("detail.companyLicense", "테스트라이센스")
                        .param("detail.companyAs", "테스트AS")
                        .param("detail.companyCareer", "테스트경력")
                        .param("detail.companyIntro", "테스트소개")
                        .param("location.locationAddr", "서울시 구로구")
                        .characterEncoding("UTF-8")
                        .with(user("companyIT002").roles("MEMBER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/interior/ihome"));
    }

    @DisplayName("업체 등록 실패시, 등록폭 재표시- interior/create-company-form")
    @Test
    void create_fail_showAgain_form() throws Exception {
        doThrow(RuntimeException.class).when(companyService).createCompany(any(), any(), any());

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        mvc.perform(multipart("/interior/new-company")
                .file(file)
                .param("detail.companyName", "테스트업체")
                .param("detail.companyAddr", "테스트주소")
                .param("detail.companyField", "테스트필드")
                .param("detail.companyLicense", "테스트라이센스")
                .param("detail.companyAs", "테스트AS")
                .param("detail.companyCareer", "테스트경력")
                .param("detail.companyIntro", "테스트소개")
                .param("location.locationAddr", "서울시 구로구")
                        .characterEncoding("UTF-8")
                        .with(user("companyIT002").roles("MEMBER"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("interior/create-company-form")
        );
    }


}