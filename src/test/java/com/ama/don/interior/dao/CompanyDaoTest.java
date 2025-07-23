package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyCreateLocationDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.interior.dto.response.CompanyDetailDto;
import com.ama.don.interior.dto.response.CompanySummaryDto;
import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CompanyDaoTest extends AbstractCompanyTestSupport {

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


    @DisplayName("중복 없이 통과조건")
    @Test
    void isDuplicateCompanyName() {
        String otherCompanyName = "중복없는업체이름";
        Boolean isDuplicate = companyDao.isDuplicateCompanyName(otherCompanyName);

        assertThat(isDuplicate).isFalse();
    }

    @DisplayName("중복된 이름 실패조건")
    @Test
    void isDuplicateCompanyNameFail() {
        String duplicateCompanyName = "중복업체이름";
        companyDao.insertCompanyDetail(createTestCompanyDetail(duplicateCompanyName));

        Boolean isDuplicate = companyDao.isDuplicateCompanyName(duplicateCompanyName);

        assertThat(isDuplicate).isTrue();
    }

    @DisplayName("로그인된 user_id로 company_id 찾기")
    @Test
    void findCompanyIdByUserId() {
        // given
        JoinformDto user = createTestUser();
        System.out.println("%%%%%%%%%%%: " + user.getUserId());

        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();
        CompanyInsertDto dto = new CompanyInsertDto();

        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());
        dto.setCompanyImg("images/interior/test.img");

        companyDao.insertCompany(dto);
        Long expectedCompanyId = dto.getCompanyId();

        Optional<Long> result = companyDao.findCompanyIdByUserId(user.getUserId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedCompanyId);
        System.out.println("############: " + result.get());
    }

    @DisplayName("회원이 업체가입안한 상태로 company_id 찾고 실패 경우")
    @Test
    void findCompanyIdByUserIdFail() {
        JoinformDto user = createTestUser();
        Optional<Long> companyId = companyDao.findCompanyIdByUserId(user.getUserId());

        assertThat(companyId).isEmpty();
    }

    @DisplayName("업체 상세정보의 CompanyDetailDto 보기")
    @Test
    void selectDetailCompany() {
        // given, 업체 등록 후 company_id 확보
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();
        CompanyInsertDto dto = new CompanyInsertDto();

        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());
        dto.setCompanyImg("images/interior/test.img");

        companyDao.insertCompany(dto);
        Long companyId = dto.getCompanyId();

        // when, CompanyDetail에 company_id로 값 등록
        CompanyDetailDto result = companyDao.selectDetailCompany(companyId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCompanyId()).isEqualTo(companyId);
        assertThat(result.getCompanyAddr()).isEqualTo(detail.getCompanyAddr());
        assertThat(result.getCompanyField()).isEqualTo(detail.getCompanyField());
        assertThat(result.getCompanyLicense()).isEqualTo(detail.getCompanyLicense());
        assertThat(result.getCompanyAs()).isEqualTo(detail.getCompanyAs());
        assertThat(result.getCompanyCareer()).isEqualTo(detail.getCompanyCareer());
    }

    @DisplayName("업체 상세페이지의 좌측 요약 정보 박스(정보 + 별점), 별점은" +
            "기입이 없을시 null 가능")
    @Test
    void selectSummaryCompany() {
        // given
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());
        dto.setCompanyImg("images/interior/test.img");

        companyDao.insertCompany(dto);
        Long companyId = dto.getCompanyId();

        // when
        CompanySummaryDto result = companyDao.selectSummaryCompany(companyId);

        // then
        assertThat(result).isNotNull();     // 모든값이 null일때
        assertThat(result.getCompanyId()).isEqualTo(companyId);
        assertThat(result.getCompanyName()).isEqualTo(detail.getCompanyName());
        assertThat(result.getCompanyIntro()).isEqualTo(detail.getCompanyIntro());
        assertThat(result.getCompanyAddr()).isEqualTo(detail.getCompanyAddr());
        assertThat(result.getCompanyLicense()).isEqualTo(detail.getCompanyLicense());
        assertThat(result.getCompanyField()).isEqualTo(detail.getCompanyField());
        assertThat(result.getCompanyRate()).isNull();
    }

}
