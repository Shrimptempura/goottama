package com.ama.don.admin.dao;

import com.ama.don.admin.dto.commentsForAdminDTO.CommentsForAdminDTO;
import com.ama.don.admin.dto.commentsForAdminDTO.CommentsSearchForAdminDTO;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchCommentIDao {

    /**
     * 전체 댓글 반환
     * @param searchVO
     * @return
     */
    List<CommentsForAdminDTO> getAllComment(@Param("searchVO") SearchVO searchVO);

    /**
     * 전체 댓글 개수 반환
     * @return
     */
    int countAllComment();

    /**
     * 댓글 아이디를 통한 댓글 조회
     * @param commentId
     * @return
     */
    CommentsForAdminDTO getCommentByCommentId(Long commentId);

    /**
     * 댓글 검색 결과
     * @param searchVO
     * @param commentsSearchForAdminDTO
     * @return
     */
    List<CommentsForAdminDTO> searchComments(@Param("searchVO") SearchVO searchVO,
                                                   @Param("commentsSearchForAdminDTO") CommentsSearchForAdminDTO commentsSearchForAdminDTO);

    /**
     * 댓글 검색 결과 개수
     * @param commentsSearchForAdminDTO
     * @return
     */
    int countSearchComment(@Param("commentsSearchForAdminDTO") CommentsSearchForAdminDTO commentsSearchForAdminDTO);

    int countNewCommentsLast24Hours();
}
