package com.ama.don.interior.dao;

import com.ama.don.interior.dto.response.CompanyReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

// 사용자가 업체에 쓰는 리뷰 dao
@Mapper
public interface CompanyReviewDao {
    // 리뷰 목록 보기, 소개글, 별점, 내용 등..
    ArrayList<CompanyReviewDto> list(Long companyId);

    // 리뷰 작성
    void create(CompanyReviewDto dto);

    // 리뷰 상세보기
    CompanyReviewDto detail(Long reviewId);

    // 리뷰 삭제
    void delete(Long reviewId);

    // 리뷰 수정
    void update(CompanyReviewDto dto);

    // 리뷰 수정 뷰
    CompanyReviewDto getEditView(Long reviewId);
}
