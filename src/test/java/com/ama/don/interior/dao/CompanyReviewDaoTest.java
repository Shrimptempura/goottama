package com.ama.don.interior.dao;

import com.ama.don.common.dao.ReviewDao;
import com.ama.don.common.dto.ReviewDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyReviewDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyReviewDao companyReviewDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("리뷰 작성 전 공통리뷰 먼저 생성후 업체 리뷰 작성")
    @Test
    void insertCommonReview() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet result = insertPolyReviewAndCompanyReview();
        Long companyReviewId = result.getCompanyReviewDto().getReviewId();
        Long commonReviewId = result.getCommonReviewDto().getReviewId();

        assertThat(companyReviewId).isNotNull();
        assertThat(commonReviewId).isNotNull();
        assertThat(companyReviewId).isEqualTo(commonReviewId);
    }

    // isExistScoreTable + insertScoreTable
    @DisplayName("리뷰 작성시 첫번째 리뷰면 생성해야 함")
    @Test
    void insertCompanyReview() {
        CreateReviewSet dto = insertPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();

        // 무조건 테이블이 비어있다고 가정
        jdbcTemplate.update("DELETE FROM company_score_avg WHERE company_id = ?", companyId);

        companyReviewDao.insertScoreTable(dto.getCompanyReviewDto());

        boolean exists = companyReviewDao.isExistScoreTable(dto.getCompanyReviewDto().getCompanyId());

        Double avg =  jdbcTemplate.queryForObject(
                "SELECT avg_communication FROM company_score_avg WHERE company_id = ?",
                Double.class, companyId
        );

        assertThat(exists).isTrue();
        assertThat(avg).isEqualTo(dto.getCompanyReviewDto().getCommunicationRate());
    }


    @DisplayName("업체 리뷰후 평균점수 계산")
    @Test
    void selectCompanyAvgRate() {
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        Long companyId = dto.getCompanyId();

        CompanyReviewCreateDto reviewDto = new CompanyReviewCreateDto();
        reviewDto.setCompanyId(companyId);
        reviewDto.setCommunicationRate(4);
        reviewDto.setPriceRate(4);
        reviewDto.setResultRate(5);
        reviewDto.setScheduleRate(5);
    }

}