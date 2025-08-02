package com.ama.don.interior.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CompanyPostLikeDaoTest {

    @Autowired
    CompanyPostLikeDao companyPostLikeDao;

    @DisplayName("회원과 게시글 좋아요 여부 확인")
    @Test
    void isLikedCompanyPost() {
        //
    }

}