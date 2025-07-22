package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyCreateLocationDto;
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

    @Test
    void insertCompanyDetail() {
        CompanyCreateDto dto = new CompanyCreateDto();
        dto.setCompanyName("업체이름");
        dto.setCompanyAddr("업체주소");
        dto.setCompanyField("업체필드");
        dto.setCompanyLicense("업체라이센스");
        dto.setCompanyAs("업체AS");
        dto.setCompanyCareer("업체경력");
        dto.setCompanyIntro("업체소개글");

        companyDao.insertCompanyDetail(dto);

        assertThat(dto.getCompanyDetailId()).isNotNull();
    }

    @Test
    void insertLocation() {
        CompanyCreateLocationDto dto = new CompanyCreateLocationDto();
        dto.setLocationAddr("서울특별시 구로구");

        companyDao.insertLocation(dto);

        assertThat(dto.getLocationId()).isNotNull();
    }
}