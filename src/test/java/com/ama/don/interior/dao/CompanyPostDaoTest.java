package com.ama.don.interior.dao;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyPostDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyPostDao companyPostDao;

    @Autowired
    PostDao postDao;

    @DisplayName("게시글 다형성 작성")
    @Test
    void insertPolyPostForCompany() {
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        Long companyId = dto.getCompanyId();
        Long userId = dto.getUserId();

        PostDto polyPost = new PostDto();
        polyPost.setUser_id(userId);
        polyPost.setTargetId(companyId);
        polyPost.setTargetType(TargetType.valueOf("INTERIOR"));

        postDao.insertPolyPostForCompany(polyPost);
        assertThat(polyPost.getPost_id()).isNotNull();
        assertThat(polyPost.getTargetId()).isEqualTo(companyId);
    }

}