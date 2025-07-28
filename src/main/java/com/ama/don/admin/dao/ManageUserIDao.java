package com.ama.don.admin.dao;

import com.ama.don.admin.dto.UserSearchVO;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManageUserIDao {

    public List<MemberDto> getAllUsers(@Param("searchVO") SearchVO searchVO);

    public int countAllUsers();

    public MemberDto getUserByUserId(String userId);

    public List<MemberDto> searchUsers(@Param("searchVO") SearchVO searchVO,
                                       @Param("userSearchVO")UserSearchVO userSearchVO);

    public int countSearchUsers(@Param("userSearchVO")UserSearchVO userSearchVO);
}
