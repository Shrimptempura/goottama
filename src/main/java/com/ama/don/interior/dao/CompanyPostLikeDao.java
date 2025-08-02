package com.ama.don.interior.dao;

import com.ama.don.interior.dto.response.CompanyPostLikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyPostLikeDao {

    // 회원이 게시글 좋아요
    int insertLikeCompanyPost(CompanyPostLikeDto dto);

    // 회원이 게시글 좋아요 취소
    int deleteLikeCompanyPost(CompanyPostLikeDto dto);

    // 회원이 게시글에 좋아요 여부 확인
    boolean isLikedCompanyPost(CompanyPostLikeDto dto);

    // 마이페이지에서 내가 좋아요한 게시글 리스트만 보여줄려면
    // select * in 고려, (모든 게시글을 좋아요 여부 확인은 성능 부하)
}
