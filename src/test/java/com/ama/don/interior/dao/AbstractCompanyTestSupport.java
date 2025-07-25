package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyCreateLocationDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.member.dto.JoinformDto;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractCompanyTestSupport {

    @Autowired
    protected CompanyDao companyDao;

    protected JoinformDto createTestUser() {
        return createTestUser("테스트아이디");
    }

    protected JoinformDto createTestUser(String loginId) {
        JoinformDto dto = new JoinformDto();
        dto.setLoginId(loginId);
        dto.setPw("abcdefghi!@");
        dto.setPw2("abcdefghi!@");

        dto.setName("홍길동");
        dto.setNickname("테스트닉네임");
        dto.setGender(JoinformDto.Gender.M);
        dto.setBirth("1999-09-09");
        dto.setTel("010-1234-5678");

        dto.setZipcode("12345");
        dto.setAddr("서울특별시 구로구");
        dto.setDetailAddr("은마아파트 123동");
        dto.combineAddress();

        dto.setEmailId("abcdefg");
        dto.setEmailDomain("naver.com");
        dto.combineEmail();

        companyDao.insertUser(dto);

        return dto;
    }

    protected CompanyCreateDto createTestCompanyDetail() {
        return createTestCompanyDetail("업체이름");
    }

    protected CompanyCreateDto createTestCompanyDetail(String companyName) {
        CompanyCreateDto dto = new CompanyCreateDto();
        dto.setCompanyName(companyName);
        dto.setCompanyAddr("업체주소");
        dto.setCompanyField("업체필드");
        dto.setCompanyLicense("업체라이센스");
        dto.setCompanyAs("업체AS");
        dto.setCompanyCareer("업체경력");
        dto.setCompanyIntro("업체소개글");

        companyDao.insertCompanyDetail(dto);

        return dto;
    }

    protected CompanyCreateLocationDto createTestLocation() {
        CompanyCreateLocationDto dto = new CompanyCreateLocationDto();
        dto.setLocationAddr("서울특별시 구로구");

        companyDao.insertLocation(dto);

        return dto;
    }

    protected CompanyInsertDto insertTestCompanyWithUserLocationAndDetail() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        return dto;
    }

    protected CompanyInsertDto insertTestCompanyWithUserLocationAndDetail(String loginId, String companyName) {
        JoinformDto user = createTestUser(loginId);
        CompanyCreateDto detail = createTestCompanyDetail(companyName);
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        return dto;
    }

    protected TestCompanyContext insertTestCompanyContext() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        return new TestCompanyContext(user, detail, location, dto);
    }
}
