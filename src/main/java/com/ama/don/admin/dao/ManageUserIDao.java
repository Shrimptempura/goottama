package com.ama.don.admin.dao;

import com.ama.don.admin.dto.UserSearchVO;
import com.ama.don.member.dto.UserDtailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManageUserIDao {
    /**
     * 전체 유저 반환
     * @see #countAllUsers()
     * @author 정순석
     */
    public List<UserDtailDto> getAllUsers();

    /**
     * 전체 유저 수 반환
     * @see #getAllUsers()
     * @author 정순석
     */
    public int countAllUsers();

    /**
     * 전체 유저의 최소 정보만 반환
     * @see #getAllUsers()
     * @author 정순석
     */
    public List<UserDtailDto> getUserMin();

    /**
     * 유저 아이디로 검색된 유저 정보 반환
     * @see #getAllUsers()
     * @author 정순석
     */
    public UserDtailDto getUserByUserId(long user_id);

    /**
     * 유저 검색. 검색 조건은 이름, 닉네임, 성별, 생일 범위, 가입일 범위, 전화번호, 우편번호, 주소, 이메일로 가능
     * 모든 값은 범위 혹은 부분일치를 검사함.
     * @see #countSearchUsers(UserSearchVO)
     * @author 정순석
     */
    public List<UserDtailDto> searchUsers(UserSearchVO userSearchVO);

    /**
     * 검색 결과 개수 반환
     * @see #searchUsers(UserSearchVO)
     * @author 정순석
     */
    public int countSearchUsers(UserSearchVO userSearchVO);
}
