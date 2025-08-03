package com.ama.don.interior.dao;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.CompanyCommentCreateDto;
import com.ama.don.interior.dto.request.CompanyCommentUpdateDto;
import com.ama.don.interior.dto.request.CompanyPostCreateDto;
import com.ama.don.interior.dto.response.CompanyCommentDto;
import com.ama.don.interior.dto.response.CompanyCommentTreeDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    @DisplayName("게시글 내 댓글 전체 조회")
    @Test
    void findCommentsByPostId() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        // 3명의 회원 생성
        JoinformDto firstUser = createTestUser("testUser111");
        JoinformDto secondUser = createTestUser("testUser222");
        JoinformDto thirdUser = createTestUser("testUser333");

        Long firstUserId = firstUser.getUserId();
        Long secondUserId = secondUser.getUserId();
        Long thirdUserId = thirdUser.getUserId();

        // 작성자가 다른 3개의 댓글 생성
        CompanyCommentCreateDto firstComment = createComment(firstUserId, companyPostId, "첫 번째 댓글");
        CompanyCommentCreateDto secondComment = createComment(secondUserId, companyPostId, "두 번째 댓글");
        CompanyCommentCreateDto thirdComment = createComment(thirdUserId, companyPostId, "세 번째 댓글");

        // 댓글 리스트
        List<CompanyCommentTreeDto> list = companyCommentDao.findCommentsByPostId(companyPostId);

        assertThat(list.size()).isEqualTo(3);
        assertThat(list)
                .extracting(CompanyCommentTreeDto::getCommentContent)
                .contains("첫 번째 댓글", "두 번째 댓글", "세 번째 댓글");
    }

    @DisplayName("댓글 수정하기")
    @Test
    void updateCompanyPostComment() throws InterruptedException {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        JoinformDto otherUser = createTestUser("testUser111");
        Long otherUserId = otherUser.getUserId();

        // 댓글 생성
        CompanyCommentCreateDto comment = createComment(otherUserId, companyPostId, "기존의 댓글 내용");

        // 댓글 조회
        CompanyCommentDto getComment = companyCommentDao.findById(comment.getCommentId());
        LocalDateTime originTime = getComment.getCreatedAt();
        assertThat(getComment).isNotNull();
        
        // 생성일과 수정일 비교를 위해 1초 대기
        Thread.sleep(1000);

        CompanyCommentUpdateDto updateComment = new CompanyCommentUpdateDto();
        updateComment.setCommentContent("수정된 댓글임");
        updateComment.setCommentId(comment.getCommentId());
        updateComment.setUserId(otherUserId);
        
        // 댓글 내용 수정
        companyCommentDao.updateCompanyComment(updateComment);

        // 댓글 조회
        getComment = companyCommentDao.findById(comment.getCommentId());
        LocalDateTime updatedTime = getComment.getModifiedAt();

        assertThat(getComment.getCommentContent()).isEqualTo("수정된 댓글임");
        assertThat(originTime.truncatedTo(ChronoUnit.SECONDS)).isNotEqualTo(updatedTime.truncatedTo(ChronoUnit.SECONDS));
    }

    @DisplayName("댓글 소프트 삭제, 내용 변경은 서비스에서")
    @Test
    void softDeleteCompanyPostComment() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        JoinformDto otherUser = createTestUser("testUser111");
        Long otherUserId = otherUser.getUserId();

        // 댓글 생성
        CompanyCommentCreateDto comment = createComment(otherUserId, companyPostId, "기존의 댓글 내용");

        // 댓글 조회
        CompanyCommentDto getComment = companyCommentDao.findById(comment.getCommentId());
        assertThat(getComment).isNotNull();
        Long commentId = getComment.getCommentId();

        // 댓글 삭제
        int deleted = companyCommentDao.softDeleteCompanyComment(commentId, otherUserId);
        getComment = companyCommentDao.findById(commentId);

        assertThat(deleted).isEqualTo(1);
        assertThat(getComment.isDeleted()).isEqualTo(true);
    }

}