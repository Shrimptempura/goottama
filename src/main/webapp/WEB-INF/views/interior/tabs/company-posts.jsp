<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 내 게시글 탭</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-posts.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<div class="cp-head">
    <div class="cp-title">게시글</div>
    <c:if test="${isOwner}">
        <a class="btn" href="${pageContext.request.contextPath}/interior/myhome/${companyId}/posts/new">게시글 작성</a>
    </c:if>
</div>

<c:choose>
    <c:when test="${empty posts}">
        <div class="cp-empty">아직 등록된 게시글이 없습니다.</div>
    </c:when>
    <c:otherwise>
        <div class="cp-grid">

            <c:forEach items="${posts}" var="p">
                <c:url var="detailUrl" value="/interior/posts/${p.companyPostId}"/>

                <a class="cp-card" href="${detailUrl}">
                    <c:choose>
                        <c:when test="${not empty p.thumbnail}">
                            <div class="cp-thumb">
                                <img src="/upload/interior_post/${p.thumbnail.file_name}" alt="thumbnail">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="cp-thumb placeholder"></div>
                        </c:otherwise>
                    </c:choose>

                    <div class="cp-body">
                        <div class="cp-title-line">${p.companyPostTitle}</div>
                        <div class="cp-meta">
                            <span>조회 ${p.companyPostCount}</span>
                            <span class="cp-dot"></span>
                            <span>스크랩 ${p.scrapCount}</span>
                        </div>
                    </div>
                </a>
            </c:forEach>

        </div>
    </c:otherwise>
</c:choose>

</body>
</html>
