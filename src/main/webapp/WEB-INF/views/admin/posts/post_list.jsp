<!-- post_list.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="info">
    전체 글 : ${searchVO.totRow} <br>
    현재 페이지 / 전체 페이지 : ${searchVO.page } / ${searchVO.totPage }
</div>
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
                <a href="#" class="nav first page-btn <c:if test='${searchVO.page == 1}'>disabled</c:if>'" data-page="1">처음</a>
        <a href="#" class="nav prev page-btn <c:if test='${searchVO.page == 1}'>disabled</c:if>'" data-page="${prevPage}">이전</a>

        <span class="spacer"></span>
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
                <a href="#" class="nav next page-btn <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" data-page="${nextPage}">다음</a>
        <a href="#" class="nav last page-btn <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" data-page="${searchVO.totPage}">맨끝</a>
    </div>
</div>

<table class="post-table">
    <thead>
        <tr>
            <th>게시글 아이디</th>
            <th>유저 아이디</th>
            <th>제목</th>
            <th>타겟 타입</th>
            <th>작성일</th>
            <th>상세 보기</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${mapList}" var="post">
            <tr>
                <td>${post.post_id}</td>
                <td>${post.user_id}</td>
                <td>${post.post_title}</td>
                <td>${post.targetType}</td>
                <td>
                    <c:if test="${not empty post.post_date}">
                        <fmt:parseDate value="${post.post_date}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                        <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/>
                    </c:if>
                </td>
                <td>
                    <button type="button" class="open-modal-btn" data-modal-target="/admin/posts/post_data_modal"
                        data-param-name="postId" data-param-value='${post.post_id}'> ▶ </button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>