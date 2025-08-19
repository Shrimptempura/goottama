package com.ama.don.common.service;

import com.ama.don.common.dto.PostDto;

public interface PostService {

    // 다형성 생성
    Long insertPolyPostForCompany(Long targetId);

    // 조회
    PostDto findById(Long postId);

    // 다형성 디폴트 조회
    int polyFindById(Long postId);
}
