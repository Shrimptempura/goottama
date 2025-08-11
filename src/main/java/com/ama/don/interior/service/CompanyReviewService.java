package com.ama.don.interior.service;

import com.ama.don.interior.dto.review.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyReviewService {

    // 리뷰 작성
    Long createReview(CompanyReviewCreateDto createReviewDto, List<MultipartFile> files);

    // 리뷰 상세 보기
    CompanyReviewDto getReviewDetail(Long reviewId);
    
    // 홈에서 보는 리뷰 리스트(최신)
    List<CompanyHomeReviewDto> findRecentForHome();
    
    // 업체 상세페이지에서 보는 리뷰 리스트
    List<CompanyReviewDto> listByCompanyId(Long companyId);

    // 업체 별점 조회
    CompanyScoreAvgDto findScoreAvgByCompanyId(Long companyId);

    // 수정
    void updateReview(CompanyReviewUpdateDto updateReviewDto, List<MultipartFile> files);

    // 리뷰 삭제(소프트)
    void deleteReview(Long reviewId);
}
