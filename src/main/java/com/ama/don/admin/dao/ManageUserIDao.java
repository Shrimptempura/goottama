package com.ama.don.admin.dao;

import com.ama.don.admin.dto.userDTO.UserSearchDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface ManageUserIDao {

    public List<UserTotalDataDTO> getAllUsers(@Param("searchVO") SearchVO searchVO);

    public int countAllUsers();

    public UserTotalDataDTO getUserByUserId(String userId);

    public UserTotalDataDTO getUserByLoginId(String loginId);

    public List<UserTotalDataDTO> searchUsers(@Param("searchVO") SearchVO searchVO,
                                       @Param("userSearchDTO") UserSearchDTO userSearchDTO);

    public int countSearchUsers(@Param("userSearchDTO") UserSearchDTO userSearchDTO);

    /**
     * 유저 아이디를 기반으로 재제 종료일을 변경
     * @param userId
     * @param endDate
     * @return
     */
    public int updateUserSanctionsUntil(String userId, Timestamp endDate);

    /**
     * 유저 아이디를 기반으로 활성 상태, 재제 종료일을 변경
     * @param userId
     * @param endDate
     * @param userStatus
     * @return
     */
    public int updateUserSanctionsAndStatus(String userId, Timestamp endDate, String userStatus);

    /**
     * 현재 시간을 기준으로 만료된 제재를 찾아 업데이트
     * @return
     */
    public int resetExpiredUserSanctions();

    /**
     * 유저 등급 변경
     * @param userId
     * @param role
     * @return
     */
    public int changeUserRole(Long userId, Long role);

    public int countNewUsersLast24Hours();

    public int countNewUsersLast7Days();

    List<Map<String, Object>> getDailyUserRegistrations();
}
