package com.ama.don.interior.dao;

import com.ama.don.interior.dto.post.CompanyPostCreateDto;
import com.ama.don.interior.dto.post.CompanyPostLikeDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyPostLikeDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyPostLikeDao companyPostLikeDao;

    @DisplayName("좋아요를 안누른 회원의 false 확인")
    @Test
    void shouldReturnFalse_whenNotLiked() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long postId = post.getCompanyPostId();

        // 회원 생성
        JoinformDto user = createTestUser("testUser111");

        CompanyPostLikeDto likeDto = new CompanyPostLikeDto();
        likeDto.setUserId(user.getUserId());
        likeDto.setCompanyPostId(postId);

        boolean isLiked = companyPostLikeDao.isLikedCompanyPost(likeDto);

        // 아직 좋아요 안누름
        assertThat(isLiked).isFalse();
    }

    @DisplayName("회원이 게시글에 좋아요를 눌렀을때 확인")
    @Test
    void shouldReturnTrue_whenLiked() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long postId = post.getCompanyPostId();

        // 회원 생성
        JoinformDto user = createTestUser("testUser111");

        // 좋아요 누름
        CompanyPostLikeDto likeDto = new CompanyPostLikeDto();
        likeDto.setUserId(user.getUserId());
        likeDto.setCompanyPostId(postId);

        int liked = companyPostLikeDao.insertLikeCompanyPost(likeDto);

        assertThat(liked).isEqualTo(1);
    }

    @DisplayName("회원이 게시글 좋아요 취소")
    @Test
    void deleteLikeCompanyPost() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long postId = post.getCompanyPostId();

        // 회원 생성
        JoinformDto user = createTestUser("testUser111");

        CompanyPostLikeDto likeDto = new CompanyPostLikeDto();
        likeDto.setUserId(user.getUserId());
        likeDto.setCompanyPostId(postId);

        // 좋아요 누름
        int liked = companyPostLikeDao.insertLikeCompanyPost(likeDto);
        assertThat(liked).isEqualTo(1);

        // 좋아요 취소
        int canceled = companyPostLikeDao.deleteLikeCompanyPost(likeDto);
        assertThat(canceled).isEqualTo(1);

        // 좋아요 확인
        boolean check = companyPostLikeDao.isLikedCompanyPost(likeDto);
        assertThat(check).isFalse();
    }

    @DisplayName("게시글 좋아요 증가 감소 숫자확인")
    @Test
    void companyPostLikeCountUpdate() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long postId = post.getCompanyPostId();

        // 회원 생성
        JoinformDto user = createTestUser("testUesr111");

        CompanyPostLikeDto likeDto = new CompanyPostLikeDto();
        likeDto.setUserId(user.getUserId());
        likeDto.setCompanyPostId(postId);

        // 회원이 좋아요을 누름
        companyPostLikeDao.insertLikeCompanyPost(likeDto);
        // 좋아요 증가
        companyPostLikeDao.incrementLikeCount(postId);
        // 해당 게시글 좋아요 숫자 확인
        int liked = companyPostLikeDao.countLikeCompanyPost(postId);
        assertThat(liked).isEqualTo(1);

        // 좋아요 취소
        companyPostLikeDao.decrementLikeCount(postId);
        int canceled = companyPostLikeDao.countLikeCompanyPost(postId);
        assertThat(canceled).isEqualTo(0);
    }
}