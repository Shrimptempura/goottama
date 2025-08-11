package com.ama.don.interior.service;

import com.ama.don.interior.dao.CompanyReviewDao;
import com.ama.don.interior.dto.review.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyReviewServiceImpl implements CompanyReviewService {

    private final CompanyReviewDao companyReviewDao;


    @Override
    public Long createReview(CompanyReviewCreateDto createReviewDto) {
        return 0L;
    }

    @Override
    public CompanyReviewDto getReviewDetail(Long reviewId) {
        return null;
    }

    @Override
    public List<CompanyHomeReviewDto> findRecentForHome() {
        return List.of();
    }

    @Override
    public List<CompanyReviewDto> listByCompanyId(Long companyId) {
        return List.of();
    }

    @Override
    public CompanyScoreAvgDto findScoreAvgByCompanyId(Long companyId) {
        return null;
    }

    @Override
    public void updateReview(CompanyReviewUpdateDto updateReviewDto) {

    }

    @Override
    public void deleteReview(Long reviewId) {

    }
}
