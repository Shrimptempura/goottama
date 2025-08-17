<!-- comment_list.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="info">
    전체 글 : ${searchVO.totRow} <br>
    현재 페이지 / 전체 페이지 : ${searchVO.page } / ${searchVO.totPage }
</div>
<!-- Pagination -->
<c:set var="prevPage" value="${searchVO.page - 1}" />
<c:if test="${prevPage < 1}">
    <c:set var="prevPage" value="1" />
</c:if>

<c:set var="nextPage" value="${searchVO.page + 1}" />
<c:if test="${nextPage > searchVO.totPage}">
    <c:set var="nextPage" value="${searchVO.totPage}" />
</c:if>

<div class="pagination-wrapper">
    <div class="pagination-controls">
        <!-- 처음/이전 -->
        <a href="#" class="nav first page-btn <c:if test='${searchVO.page == 1}'>disabled</c:if>'" data-page="1">처음</a>
        <a href="#" class="nav prev page-btn <c:if test='${searchVO.page == 1}'>disabled</c:if>'" data-page="${prevPage}">이전</a>

        <span class="spacer"></span>
        <!-- 페이지 숫자 버튼 -->
        <c:forEach begin="${searchVO.pageStart}" end="${searchVO.pageEnd}" var="i">
            <c:choose>
                <c:when test="${i eq searchVO.page}">
                    <span class="current">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="#" class="otherpages page-btn" data-page="${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <span class="spacer"></span>
        <!-- 다음/맨끝 -->
        <a href="#" class="nav next page-btn <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" data-page="${nextPage}">다음</a>
        <a href="#" class="nav last page-btn <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" data-page="${searchVO.totPage}">맨끝</a>
    </div>
</div>

<!-- 댓글 목록 출력 -->
<table class="comment-table">
    <thead>
        <tr>
            <th>댓글 아이디</th>
            <th>유저 아이디</th>
            <th>댓글 내용</th>
            <th>타겟 타입</th>
            <th>작성일</th>
            <th>삭제 여부</th>
            <th>상세 보기</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${mapList}" var="comment">
            <tr>
                <td>${comment.commentId}</td>
                <td>${comment.userId}</td>
                <td>${comment.commentContent}</td>
                <td>${comment.targetType}</td>
                <td><fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>
                    <c:choose>
                        <c:when test="${comment.isDeleted}">삭제됨</c:when>
                        <c:otherwise>정상</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <button type="button" class="open-modal-btn" data-modal-target="/admin/comments/comment_data_modal"
                        data-param-name="commentId" data-param-value='${comment.commentId}'> ▶ </button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>