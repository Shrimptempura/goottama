package com.ama.don.admin.dao;

import com.ama.don.admin.dto.userDTO.UserSearchDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManageUserIDao {

    public List<UserTotalDataDTO> getAllUsers(@Param("searchVO") SearchVO searchVO);

    public int countAllUsers();

    public UserTotalDataDTO getUserByUserId(String userId);

    public UserTotalDataDTO getUserByLoginId(String loginId);

    public List<UserTotalDataDTO> searchUsers(@Param("searchVO") SearchVO searchVO,
                                       @Param("userSearchDTO") UserSearchDTO userSearchDTO);

    public int countSearchUsers(@Param("userSearchDTO") UserSearchDTO userSearchDTO);
}
