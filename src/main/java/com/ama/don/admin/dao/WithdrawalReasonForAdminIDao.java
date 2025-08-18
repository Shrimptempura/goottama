package com.ama.don.admin.dao;

import com.ama.don.admin.dto.userDTO.WithdrawalReasonDTO;
import com.ama.don.admin.dto.userDTO.WithdrawalReasonSearchDTO;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WithdrawalReasonForAdminIDao {
    /**
     * 전체 탈퇴 이유 반환
     * @param searchVO
     * @return
     */
    List<WithdrawalReasonDTO> getAllWithdrawalReason(@Param("searchVO") SearchVO searchVO);

    /**
     * 전체 탈퇴 이유 개수 반환
     * @return
     */
    int countAllWithdrawalReason();

    /**
     * 탈퇴 아이디를 통한 탈퇴 이유 조회
     * @param withdrawalId
     * @return
     */
    WithdrawalReasonDTO getAllWithdrawalReasonByWithdrawalId(Long withdrawalId);

    /**
     * 검색 결과
     * @param searchVO
     * @param withdrawalReasonSearchDTO
     * @return
     */
    List<WithdrawalReasonDTO> searchWithdrawalReason(@Param("searchVO") SearchVO searchVO,
                                                     @Param("withdrawalReasonSearchDTO")WithdrawalReasonSearchDTO withdrawalReasonSearchDTO);

    /**
     * 검색 결과 개수 반환
     * @param withdrawalReasonSearchDTO
     * @return
     */
    int countSearchWithdrawalReason(@Param("withdrawalReasonSearchDTO")WithdrawalReasonSearchDTO withdrawalReasonSearchDTO);
}
