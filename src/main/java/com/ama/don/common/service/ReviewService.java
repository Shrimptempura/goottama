package com.ama.don.common.service;

import com.ama.don.common.dto.ReviewDto;

public interface ReviewService {

    // 다형성 리뷰 작성(일단 인테리어 사용)
    Long insertPolyReview(ReviewDto dto);

    // review 테이블의 default 조회
    ReviewDto selectDefaultFieldById(Long reviewId);

    // review 테이블 전체 조회
    ReviewDto selectAllFieldById(Long reviewId);

}
