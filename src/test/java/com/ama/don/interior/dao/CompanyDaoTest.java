package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyCreateLocationDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CompanyDaoTest {

    @Autowired
    CompanyDao companyDao;

    @DisplayName("company_detail 테이블 생성 테스트")
    @Test
    void insertCompanyDetail() {
        CompanyCreateDto detail = createTestCompanyDetail();
        companyDao.insertCompanyDetail(detail);

        assertThat(detail.getCompanyDetailId()).isNotNull();
    }

    @DisplayName("location 테이블에서 주소만 확인")
    @Test
    void insertLocation() {
        CompanyCreateLocationDto location = createTestLocation();

        companyDao.insertLocation(location);

        assertThat(location.getLocationId()).isNotNull();
    }

    @DisplayName("여러 테이블에서 fk를 받아 생성한 company 테이블")
    @Test
    void insertCompany() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());
        dto.setCompanyImg("images/interior/test.img");

        companyDao.insertCompany(dto);

        assertThat(dto.getCompanyId()).isNotNull();
    }



    private JoinformDto createTestUser() {
        JoinformDto dto = new JoinformDto();
        dto.setLoginId("테스트아이디");
        dto.setPw("abcdefghi!@");
        dto.setPw2("abcdefghi!@");
        dto.setName("홍길동");
        dto.setNickname("테스트닉네임");
        dto.setGender(JoinformDto.Gender.M);
        dto.setBirth("1999-09-09");
        dto.setTel("010-1234-5678");
        dto.setZipcode("12345");
        dto.setAddr("서울특별시 구로구");
        dto.setEmailId("abcdefg");
        dto.setEmailDomain("naver.com");

        companyDao.insertUser(dto);

        return dto;
    }

    private CompanyCreateDto createTestCompanyDetail() {
        CompanyCreateDto dto = new CompanyCreateDto();
        dto.setCompanyName("업체이름");
        dto.setCompanyAddr("업체주소");
        dto.setCompanyField("업체필드");
        dto.setCompanyLicense("업체라이센스");
        dto.setCompanyAs("업체AS");
        dto.setCompanyCareer("업체경력");
        dto.setCompanyIntro("업체소개글");

        companyDao.insertCompanyDetail(dto);

        return dto;
    }

    private CompanyCreateLocationDto createTestLocation() {
        CompanyCreateLocationDto dto = new CompanyCreateLocationDto();
        dto.setLocationAddr("서울특별시 구로구");

        companyDao.insertLocation(dto);

        return dto;
    }

}
