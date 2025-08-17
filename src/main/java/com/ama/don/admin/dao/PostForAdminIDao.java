package com.ama.don.admin.dao;

import com.ama.don.admin.dto.postForAdminDTO.PostForAdminDTO;
import com.ama.don.admin.dto.postForAdminDTO.PostSearchForAdminDTO;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostForAdminIDao {

    List<PostForAdminDTO> getAllPost(@Param("searchVO") SearchVO searchVO);

    int countAllPost();

    PostForAdminDTO getPostByPostId(Long postId);

    List<PostForAdminDTO> searchPost(@Param("searchVO") SearchVO searchVO,
                                     @Param("postSearchForAdminDTO") PostSearchForAdminDTO postSearchForAdminDTO);

    int countSearchPost(@Param("postSearchForAdminDTO") PostSearchForAdminDTO postSearchForAdminDTO);
}
