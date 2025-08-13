package com.ama.don.admin.dao;

import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.dto.userDTO.UserActivitySearchDTO;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserActivityLogIDao {

    public List<UserActivityDto> getAllUserActivity(@Param("searchVO") SearchVO searchVO);

    public int countGetAllUserActivity();

    public UserActivityDto getUserActivityById(String activityId);

    public List<UserActivityDto> getUserActivityByUserId(String UserId, @Param("searchVO") SearchVO searchVO);

    public List<UserActivityDto> getSearchUserActivity(@Param("userActivitySearchDTO") UserActivitySearchDTO userActivitySearchDTO,
                                                       @Param("searchVO") SearchVO searchVO);

    public int countGetSearchUserActivity(@Param("userActivitySearchDTO") UserActivitySearchDTO userActivitySearchDTO);

    public int writeUserActivityLog(UserActivityDto userActivityDto);

}
