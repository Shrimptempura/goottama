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

<!-- 공지 목록 출력 -->
<table class="notice-table">
    <tr>
       <td>제목</td>
       <td>날자</td>
    </tr>
    <c:forEach items="${list}" var="notice">
        <tr>
            <td>
                <a href="/admin/notices/notice_detail?notices_id=${notice.noticesId}">
                    ${notice.noticesTitle}
                </a>
            </td>
            <td>
            <fmt:formatDate value="${notice.noticesCreatedAt}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
    </c:forEach>
</table>