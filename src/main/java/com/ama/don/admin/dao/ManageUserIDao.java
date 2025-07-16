package com.ama.don.admin.dao;

import com.ama.don.admin.dto.UserSearchVO;
import com.ama.don.member.dto.UserDtailDto;
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
     * @return 유저 상세 정보 목록 (List of {@link UserDtailDto})
     * @see #countAllUsers()
     */
    List<UserDtailDto> getAllUsers();

    /**
     * 전체 유저 수를 반환한다.
     *
     * @return 유저 수 (int)
     * @see #getAllUsers()
     */
    int countAllUsers();

    /**
     * 전체 유저의 최소 정보만 반환한다.
     * 예: 이름, 닉네임, 아이디 등 일부 필드만 포함.
     *
     * @return 유저 최소 정보 목록 (List of {@link UserDtailDto})
     * @see #getAllUsers()
     */
    List<UserDtailDto> getUserMin();

    /**
     * 사용자 ID로 유저 상세 정보를 조회한다.
     *
     * @param user_id 조회할 유저의 고유 식별자 (long)
     * @return 해당 유저의 상세 정보 (없으면 null)
     * @see #getAllUsers()
     */
    UserDtailDto getUserByUserId(long user_id);

    /**
     * 다양한 조건으로 유저를 검색한다.
     * <p>
     * 검색 조건:
     * - 이름, 닉네임, 성별
     * - 생일 및 가입일 범위
     * - 전화번호, 우편번호, 주소, 이메일
     * </p>
     * 모든 필드는 부분 일치 혹은 범위 비교로 검색됨.
     *
     * @param userSearchVO 검색 조건을 담은 VO
     * @return 조건에 맞는 유저 목록
     * @see #countSearchUsers(UserSearchVO)
     */
    List<UserDtailDto> searchUsers(UserSearchVO userSearchVO);

    /**
     * 검색 조건에 해당하는 유저 수를 반환한다.
     *
     * @param userSearchVO 검색 조건 VO
     * @return 검색된 유저 수
     * @see #searchUsers(UserSearchVO)
     */
    int countSearchUsers(UserSearchVO userSearchVO);
}
