package com.ama.don.admin.dao;

import com.ama.don.admin.dto.UserSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 관리자용 사용자 관리 DAO 인터페이스 (MyBatis Mapper).
 * <p>
 * 전체 유저 조회, 검색, 상세 정보 조회 등의 기능을 정의함.
 * </p>
 *
 * 검색 조건은 {@link UserSearchVO}를 통해 전달되며, 이름, 닉네임, 성별, 생일, 가입일, 연락처 등 다양한 필드로 검색 가능.
 *
 * @author 정순석
 */
@Mapper
public interface ManageUserIDao {

    /**
     * 전체 유저의 상세 정보를 반환한다.
     *
     * @return 유저 상세 정보 목록 (List of {@link UserDetailDto})
     * @see #countAllUsers()
     */


    /**
     * 전체 유저 수를 반환한다.
     *
     * @return 유저 수 (int)
     * @see #getAllUsers()
     */
    int countAllUsers();

 
    int countSearchUsers(UserSearchVO userSearchVO);
}
