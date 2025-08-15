//package com.ama.don.interior.dao;
//
//import com.ama.don.common.dao.PostDao;
//import com.ama.don.common.dto.PostDto;
//import com.ama.don.interior.dto.company.CompanyInsertDto;
//import com.ama.don.interior.dto.post.*;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional
//@SpringBootTest
//class CompanyPostDaoTest extends AbstractCompanyTestSupport {
//
//    @Autowired
//    CompanyPostDao companyPostDao;
//
//    @Autowired
//    PostDao postDao;
//
//    @Autowired
//    CompanyPostLikeDao companyPostLikeDao;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @DisplayName("게시글 다형성 작성")
//    @Test
//    void insertPolyPostForCompany() {
//        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
//        Long companyId = dto.getCompanyId();
//        Long userId = dto.getUserId();
//
//        // 다형성 게시글 생성
//        PostDto polyPost = createPolyPost(userId, companyId);
//
//        assertThat(polyPost.getPost_id()).isNotNull();
//        assertThat(polyPost.getTargetId()).isEqualTo(companyId);
//    }
//
//    @DisplayName("업체 게시글 생성")
//    @Test
//    void insertCompanyPost() {
//        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
//        Long companyId = dto.getCompanyId();
//        Long userId = dto.getUserId();
//
//        // 다형성 게시글 생성
//        PostDto polyPost = createPolyPost(userId, companyId);
//        Long postId = polyPost.getPost_id();
//
//        // 다형성 조회, default date 확인
//        polyPost = postDao.findById(postId);
//        assertThat(polyPost).isNotNull();
//
//        // 업체 게시글 작성
//        CompanyPostCreateDto companyPost = new CompanyPostCreateDto();
//        companyPost.setPostId(postId);
//        companyPost.setCompanyId(companyId);
//        companyPost.setCompanyPostTitle("업체 게시글 제목");
//        companyPost.setCompanyPostContent("업체 게시글 내용");
//        companyPost.setSpaceType("아파트 테스트");
//        companyPost.setAreaPyeong("34평 테스트");
//        companyPost.setStyle("내추럴 테스트");
//        companyPost.setConstructionDetail("도배시공 테스트");
//
//        companyPostDao.insertCompanyPost(companyPost);
//
//        assertThat(companyPost.getCompanyPostId()).isNotNull();
//        assertThat(companyPost.getPostId()).isEqualTo(postId);
//    }
//
//    @DisplayName("업체 게시글 상세보기 post + company_post 부분조회")
//    @Test
//    void getPostAndCompanyPostById() {
//        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
//        Long companyId = dto.getCompanyId();
//        Long userId = dto.getUserId();
//
//        // 게시글 작성
//        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);
//        Long companyPostId = companyPost.getCompanyPostId();
//        assertThat(companyPostId).isNotNull();
//
//        // 게시글 부분 조회
//        CompanyPostDetailSplitDto detail = companyPostDao.getPostAndCompanyPostById(companyPostId);
//
//        assertThat(detail).isNotNull();
//        // default 검사
//        assertThat(detail.getPostDate()).isNotNull();
//        assertThat(detail.getPostId()).isEqualTo(companyPost.getPostId());
//    }
//
//    @DisplayName("업체 게시글 상세보기중 업체 정보 부분조회")
//    @Test
//    void getCompanyBasicInfoById() {
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 작성
//        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);
//
//        // 업체 부분 조회
//        CompanyPostBasicInfoDto detail = companyPostDao.getCompanyBasicInfoById(companyId);
//        assertThat(detail).isNotNull();
//        assertThat(detail.getCompanyId()).isEqualTo(companyId);
//        assertThat(detail.getCompanyName()).isEqualTo(context.getDetail().getCompanyName());
//    }
//
//    @DisplayName("업체 게시글 수정 확인")
//    @Test
//    void updateCompanyPost() {
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 작성
//        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);
//        Long companyPostId = companyPost.getCompanyPostId();
//
//        // 게시글 수정
//        CompanyPostUpdateDto updateDto = new CompanyPostUpdateDto();
//        updateDto.setCompanyPostId(companyPostId);
//        updateDto.setCompanyPostTitle("제목이 수정됨");
//        updateDto.setCompanyPostContent("내용이 수정됨");
//        updateDto.setSpaceType("공간이 수정됨");
//        updateDto.setAreaPyeong("평수 수정됨");
//        updateDto.setStyle("스타일 수정됨");
//        updateDto.setConstructionDetail("세부내용 수정됨");
//
//        int updated = companyPostDao.updatePost(updateDto);
//
//        assertThat(updateDto).isNotNull();
//        assertThat(updated).isEqualTo(1);
//
//        // 게시글 읽기
//        CompanyPostDetailView detail = companyPostDao.getPostDetail(companyPostId);
//        String title = detail.getCompanyPostTitle();
//        String content = detail.getCompanyPostContent();
//
//        assertThat(title).isEqualTo(updateDto.getCompanyPostTitle());
//        assertThat(content).isEqualTo("내용이 수정됨");
//    }
//
//    @DisplayName("게시글 수정 뷰")
//    @Test
//    void getCompanyPostUpdateView() {
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 작성
//        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);
//        Long companyPostId = companyPost.getCompanyPostId();
//
//        // 수정 전 조회값 받음
//        CompanyPostUpdateDto updateView = companyPostDao.getEditView(companyPostId);
//
//        assertThat(updateView).isNotNull();
//        assertThat(updateView.getStyle()).isEqualTo(companyPost.getStyle());
//        assertThat(updateView.getSpaceType()).isEqualTo(companyPost.getSpaceType());
//    }
//
//    @DisplayName("조회수 증가 확인")
//    @Test
//    void increasePostHit() {
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 작성
//        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);
//        Long companyPostId = companyPost.getCompanyPostId();
//
//        CompanyPostDetailDto detail = companyPostDao.getPostAndCompanyPostById(companyPostId);
//        int first = detail.getCompanyPostCount();
//
//        // 조회수 증가
//        companyPostDao.increaseHit(companyPostId);
//
//        // 부분 조회
//        int second = companyPostDao.getPostAndCompanyPostById(companyPostId).getCompanyPostCount();
//
//        assertThat(first).isNotEqualTo(second);
//        assertThat(first).isEqualTo(second - 1);
//    }
//
//    @DisplayName("홈에서 보는 업체 게시글 최신순")
//    @Test
//    void findCompanyPostByLatest() throws InterruptedException {
//        // 업체 생성
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 생성
//        CompanyPostCreateDto firstCompanyPost = createCompanyPost(userId, companyId);
//        Thread.sleep(1000);
//        CompanyPostCreateDto secondCompanyPost = createCompanyPost(userId, companyId);
//        Thread.sleep(1000);
//        CompanyPostCreateDto thirdCompanyPost = createCompanyPost(userId, companyId);
//        Thread.sleep(1000);
//
//
//        // 홈에서 보는 업체 리스트 최신순 desc postDate
//        List<CompanyHomePostDto> homelist = companyPostDao.findCompanyPostByLatest();
//        LocalDateTime latestPostDate = homelist.get(0).getPostDate();
//        LocalDateTime middlePostDate = homelist.get(1).getPostDate();
//        LocalDateTime oldestPostDate = homelist.get(2).getPostDate();
//
//        assertThat(homelist).isNotNull();
//        assertThat(oldestPostDate).isBefore(middlePostDate);
//        assertThat(middlePostDate).isBefore(latestPostDate);
//    }
//
//    @DisplayName("홈에서 보는 랜덤 업체 게시글 - 우연히 정렬되어 실패의 가능성이 있음")
//    @Test
//    void findCompanyPostByRandom() {
//        // 업체 생성
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 6개 생성
//        for (int i = 0; i < 6; i++) {
//            createCompanyPost(userId, companyId);
//        }
//
//        // postId 뽑기
//        List<Long> ids = companyPostDao.findCompanyPostByRandom().stream()
//                .map(CompanyHomePostDto::getPostId)
//                .toList();
//
//        // 정렬된 postId
//        List<Long> sortedIds = new ArrayList<>(ids);
//        Collections.sort(sortedIds);
//
//        assertThat(ids).isNotNull();
//        assertThat(ids).isNotEqualTo(sortedIds);
//    }
//
//
//
//    @DisplayName("홈에서 보는 같은 지역구 정렬 리스트")
//    @Test
//    void findCompanyPostByRegion() {
//        // 업체 생성
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 생성
//        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
//        Long postid = post.getPostId();
//
//        List<CompanyHomePostDto> list = companyPostDao.findCompanyPostByRegion("구로구");
//        assertThat(list).isNotEmpty();
//
//        boolean hasMatchRegion = list.stream()
//                .map(CompanyHomePostDto::getRegion)
//                .anyMatch(region -> region.contains("구로구"));
//
//        assertThat(hasMatchRegion).isTrue();
//        assertThat(list.stream()
//                .anyMatch(postDto -> postDto.getPostId().equals(postid)))
//                .isTrue();
//    }
//
//    @DisplayName("상세페이지에서 보는 업체 게시글 목록 뷰")
//    @Test
//    void getCompanyPostPreview() {
//        // 업체 생성
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 생성
//        for (int i = 0; i < 8; i++) {
//            createCompanyPost(userId, companyId);
//        }
//
//        List<CompanyPostPreviewDto> list = companyPostDao.getCompanyPostPreview(companyId);
//        assertThat(list).isNotEmpty();
//        assertThat(list).hasSize(8);
//    }
//
//    @DisplayName("게시글 하위 삭제 후 다형성 삭제")
//    @Test
//    void deletePostAndPolyPost() {
//        // 업체 생성
//        TestCompanyContext context = insertTestCompanyContext();
//        Long companyId = context.getCompanyId();
//        Long userId = context.getUser().getUserId();
//
//        // 게시글 생성
//        CompanyPostCreateDto post = createCompanyPost(userId, companyId);
//        Long companyPostId = post.getCompanyPostId();
//        Long postId = post.getPostId();
//
//        // 하위 먼저 삭제
//        int subDeleted = companyPostDao.deleteCompanyPostById(companyPostId);
//        assertThat(subDeleted).isEqualTo(1);
//
//        CompanyPostDto deletedPost = companyPostDao.findById(companyPostId);
//        assertThat(deletedPost).isNull();
//
//        // 다형성 삭제
//        int parentDeleted = companyPostDao.deletePolyPostById(postId);
//        assertThat(parentDeleted).isEqualTo(1);
//
//        PostDto deletedPolyPost = postDao.findById(postId);
//        assertThat(deletedPolyPost).isNull();
//    }
//
//
//
//
//}