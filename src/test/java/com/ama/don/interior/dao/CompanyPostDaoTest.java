package com.ama.don.interior.dao;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.interior.dto.request.CompanyPostCreateDto;
import com.ama.don.interior.dto.response.CompanyPostDetailDto;
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
        
        // 다형성 게시글 생성
        PostDto polyPost = createPolyPost(userId, companyId);
        
        assertThat(polyPost.getPost_id()).isNotNull();
        assertThat(polyPost.getTargetId()).isEqualTo(companyId);
    }
    
    @DisplayName("업체 게시글 생성")
    @Test
    void insertCompanyPost() {
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        Long companyId = dto.getCompanyId();
        Long userId = dto.getUserId();

        // 다형성 게시글 생성
        PostDto polyPost = createPolyPost(userId, companyId);
        Long postId = polyPost.getPost_id();

        // 다형성 조회, default date 확인
        polyPost = postDao.findById(postId);
        assertThat(polyPost).isNotNull();

        // 업체 게시글 작성
        CompanyPostCreateDto companyPost = new CompanyPostCreateDto();
        companyPost.setPostId(postId);
        companyPost.setCompanyId(companyId);
        companyPost.setCompanyPostTitle("업체 게시글 제목");
        companyPost.setCompanyPostContent("업체 게시글 내용");
        companyPost.setSpaceType("아파트 테스트");
        companyPost.setAreaPyeong("34평 테스트");
        companyPost.setStyle("내추럴 테스트");
        companyPost.setConstructionDetail("도배시공 테스트");
        
        companyPostDao.insertCompanyPost(companyPost);

        assertThat(companyPost.getCompanyPostId()).isNotNull();
        assertThat(companyPost.getPostId()).isEqualTo(postId);
    }

    @DisplayName("업체 게시글 상세보기 post + company_post 부분조회")
    @Test
    void getPostAndCompanyPostById() {
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        Long companyId = dto.getCompanyId();
        Long userId = dto.getUserId();

        // 게시글 작성
        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);
        Long companyPostId = companyPost.getCompanyPostId();
        assertThat(companyPostId).isNotNull();

        // 게시글 부분 조회
        CompanyPostDetailDto detail = companyPostDao.getPostAndCompanyPostById(companyPostId);

        assertThat(detail).isNotNull();
        // default 검사
        assertThat(detail.getPostDate()).isNotNull();
        assertThat(detail.getPostId()).isEqualTo(companyPost.getPostId());
    }
    
    @DisplayName("업체 게시글 상세보기중 업체 정보 부분조회")
    @Test
    void getCompanyBasicInfoById() {
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();
        Long userId = context.getUser().getUserId();

        // 게시글 작성
        CompanyPostCreateDto companyPost = createCompanyPost(userId, companyId);

        // 업체 부분 조회
        CompanyPostDetailDto detail = companyPostDao.getCompanyBasicInfoById(companyId);
        assertThat(detail).isNotNull();
        assertThat(detail.getCompanyId()).isEqualTo(companyId);
        assertThat(detail.getCompanyName()).isEqualTo(context.getDetail().getCompanyName());
    }

}