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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyReviewDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyReviewDao companyReviewDao;

    @Autowired
    ReviewDao reviewDao;

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


    @DisplayName("업체 리뷰 평균점수 계산")
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