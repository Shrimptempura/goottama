package com.ama.don.interior.dao;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.CompanyCommentCreateDto;
import com.ama.don.interior.dto.request.CompanyPostCreateDto;
import com.ama.don.interior.dto.response.CompanyCommentDto;
import com.ama.don.interior.dto.response.CompanyCommentTreeDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyCommentDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyCommentDao companyCommentDao;

    @DisplayName("업체 게시글에 댓글 작성")
    @Test
    void insertComment() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long postId = post.getCompanyPostId();

        JoinformDto otherUser = createTestUser("testUser111");
        Long otherUserId = otherUser.getUserId();

        CompanyCommentCreateDto comment = new CompanyCommentCreateDto();
        comment.setUserId(otherUserId);
        comment.setCompanyPostId(postId);
        comment.setCommentContent("테스트 댓글");
        comment.setTargetId(postId);
        comment.setTargetType(TargetType.valueOf("INTERIOR"));

        // 댓글 작성
        int commented = companyCommentDao.insertCompanyComment(comment);
        assertThat(commented).isEqualTo(1);

        Long commentId = comment.getCommentId();
        CompanyCommentDto getComment = companyCommentDao.findById(commentId);

        assertThat(getComment.getCommentContent()).isEqualTo("테스트 댓글");
        assertThat(getComment.getUserId()).isEqualTo(otherUserId);
    }

}