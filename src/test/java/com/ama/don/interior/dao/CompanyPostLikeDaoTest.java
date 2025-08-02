package com.ama.don.interior.dao;

import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CompanyPostLikeDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyPostLikeDao companyPostLikeDao;

    @DisplayName("좋아요를 안누른 회원의 false 확인")
    @Test
    void isLikedCompanyPost() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        createCompanyPost(userId, companyId);

        JoinformDto user = createTestUser("testUser111");

        boolean isLiked = companyPostLikeDao.isLikedCompanyPost(companyId, user.getUserId());

        // 아직 좋아요 안누름
        assertThat(isLiked).isFalse();
    }

}