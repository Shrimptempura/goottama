package com.ama.don.common.service;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.service.CompanyAuthService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Setter
public class PostServiceImpl implements PostService {

    private final PostDao postDao;
    private final CompanyAuthService companyAuthService;

    // 다형성 생성
    @Transactional
    @Override
    public Long insertPolyPostForCompany(Long targetId) {
        Long userId = companyAuthService.getLoginUserId();

        PostDto postDto = new PostDto();
        postDto.setUser_id(userId);
        postDto.setTargetId(targetId);
        postDto.setTargetType(TargetType.INTERIOR_POST);
        postDao.insertPolyPostForCompany(postDto);

        return postDto.getPost_id();
    }

    // 전체 조회
    @Transactional(readOnly = true)
    @Override
    public PostDto findById(Long postId) {
        return postDao.findById(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public int polyFindById(Long postId) {
        return 0;
    }

}
