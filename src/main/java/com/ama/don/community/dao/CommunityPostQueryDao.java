package com.ama.don.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.community.dto.CommunityPostListDto;

@Mapper
public interface CommunityPostQueryDao {
    List<CommunityPostListDto> findMyPostsByType(@Param("userId") Long userId,
                                                 @Param("targetType") String targetType);
}
