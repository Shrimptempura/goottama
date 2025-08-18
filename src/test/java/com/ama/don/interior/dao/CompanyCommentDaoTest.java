package com.ama.don.interior.dao;

import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.comment.CompanyCommentCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentUpdateDto;
import com.ama.don.interior.dto.post.CompanyPostCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;
import com.ama.don.interior.dto.post.CompanyPostDto;
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
        List<CompanyCommentTreeDto> list = companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);

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
        assertThat(getComment.getDeleted()).isEqualTo(true);
    }

    @DisplayName("업체 게시글에 쓰는 대댓글, 깊이는 1")
    @Test
    void createDepthOneReplyComment() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        JoinformDto parentUser = createTestUser("testUser111");
        Long parentUserId = parentUser.getUserId();

        JoinformDto childUser = createTestUser("testUser222");
        Long childUserId = childUser.getUserId();

        // 댓글 생성
        CompanyCommentCreateDto comment = createComment(parentUserId, companyPostId, "대댓글 부모");
        Long parentCommentId = comment.getCommentId();

        // 대댓글 생성
        CompanyCommentCreateDto replyComment = new CompanyCommentCreateDto();
        replyComment.setUserId(childUserId);
        replyComment.setCompanyPostId(companyPostId);
        replyComment.setCommentContent("여기는 대댓글 내용");
        replyComment.setTargetId(companyPostId);
        replyComment.setTargetType(TargetType.INTERIOR);
        replyComment.setParentCommentId(parentCommentId);

        companyCommentDao.insertCompanyComment(replyComment);
        Long replyCommentId = replyComment.getCommentId();

        // 댓글 단건 읽기
        CompanyCommentDto getComment = companyCommentDao.findById(replyCommentId);

        assertThat(getComment).isNotNull();
        assertThat(getComment.getParentCommentId()).isEqualTo(parentCommentId);
        assertThat(getComment.getCommentContent()).isEqualTo("여기는 대댓글 내용");
    }

    @DisplayName("게시글 내 전체 댓글 중 대댓글 포함 조회, 깊이는 1")
    @Test
    void findDepthOneTreeCommentByPostId() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        JoinformDto parentUser = createTestUser("testUser111");
        Long parentUserId = parentUser.getUserId();

        JoinformDto childUser = createTestUser("testUser222");
        Long childUserId = childUser.getUserId();

        // 댓글 생성
        CompanyCommentCreateDto comment = createComment(parentUserId, companyPostId, "대댓글 부모");
        Long parentCommentId = comment.getCommentId();

        // 대댓글 생성
        CompanyCommentCreateDto replyComment = new CompanyCommentCreateDto();
        replyComment.setUserId(childUserId);
        replyComment.setCompanyPostId(companyPostId);
        replyComment.setCommentContent("여기는 대댓글 내용");
        replyComment.setTargetId(companyPostId);
        replyComment.setTargetType(TargetType.INTERIOR);
        replyComment.setParentCommentId(parentCommentId);

        companyCommentDao.insertCompanyComment(replyComment);
        Long replyCommentId = replyComment.getCommentId();

        // 전체 댓글 리스트 조회
        List<CompanyCommentTreeDto> list = companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        assertThat(list.size()).isEqualTo(2);

        // 부모 댓글이 있는지 확인 
        CompanyCommentTreeDto findParent = list.stream()
                .filter(c -> c.getCommentId().equals(parentCommentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("부모 댓글이 존재하지 않음"));
        
        // 부모 댓글이 있고 대댓글이 있는지 확인
        CompanyCommentTreeDto findReply = list.stream()
                .filter(c -> c.getCommentId().equals(replyCommentId))
                .findFirst() // 반환값이 Optional<T>라서 후속부터 관련 문법 가능
                .orElseThrow(() -> new IllegalStateException("대댓글 없음"));
    }

    @DisplayName("게시글 상세정보 내에서 댓글 숫자 조회")
    @Test
    void countCommentsByTarget() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        // 회원 3명 생성
        JoinformDto firstUser = createTestUser("testUser111");
        Long firstUserId = firstUser.getUserId();

        JoinformDto secondUser = createTestUser("testUser222");
        Long secondUserId = secondUser.getUserId();

        JoinformDto thirdUser = createTestUser("testUser333");
        Long thirdUserId = thirdUser.getUserId();

        // 댓글 생성
        CompanyCommentCreateDto fisrtComment = createComment(firstUserId, companyPostId, "첫번째 댓글");
        CompanyCommentCreateDto secondComment = createComment(secondUserId, companyPostId, "두번째 댓글");
        CompanyCommentCreateDto thirdComment = createComment(thirdUserId, companyPostId, "세번째 댓글");
        CompanyCommentCreateDto lastComment = createComment(thirdUserId, companyPostId, "세번째 댓글");

        // 전체 댓글 리스트 조회
        List<CompanyCommentTreeDto> list = companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        assertThat(list.size()).isEqualTo(4);

        int commentCount = companyCommentDao.countCommentsByTarget(companyPostId, TargetType.INTERIOR);
        assertThat(commentCount).isEqualTo(4);
    }

    @DisplayName("게시글 삭제 해당 댓글들 전체 소프트 삭제")
    @Test
    void softDeleteCommentsByPostId() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 생성
        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
        Long companyPostId = post.getCompanyPostId();

        // 회원 생성
        JoinformDto otherUser = createTestUser("testUser111");
        Long otherUserId = otherUser.getUserId();

        // 댓글 3개 작성
        CompanyCommentCreateDto fisrtComment = createComment(otherUserId, companyPostId, "첫번째 댓글");
        CompanyCommentCreateDto secondComment = createComment(otherUserId, companyPostId, "두번째 댓글");
        CompanyCommentCreateDto thirdComment = createComment(otherUserId, companyPostId, "세번째 댓글");

        // 댓글 조회
        List<CompanyCommentTreeDto> list = companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        assertThat(list).hasSize(3);

        // 게시글 삭제전 댓글 전체 삭제 경우
        int allDeleted = companyCommentDao.softDeleteCommentsByPostId(companyPostId, TargetType.INTERIOR);
        assertThat(allDeleted).isEqualTo(3);

        // 댓글 조회 및 확인
        for (CompanyCommentTreeDto comment : companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST)) {
            assertThat(comment.getDeleted()).isTrue();
        }
    }
}