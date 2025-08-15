<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-14
  Time: 오전 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 상세보기</title>
</head>
<body>
    <h2>post-detail</h2>

    <div>
        작성일: ${detail.post.postDate}
        조회수: ${detail.post.companyPostCount} <br/>
        <c:if test="${not empty detail.post.updatedAt}">
            수정일: ${detail.post.updatedAt}
        </c:if>
    </div>

    <div>
        제목: ${detail.post.companyPostTitle} <br/>
        내용: ${detail.post.companyPostContent} <br/>
    </div>

    <div>
        업체 정보 <br/>
        업체 이름: ${detail.company.companyName} <br/>
        소개: ${detail.company.companyIntro} <br/>
        공간 타입: ${detail.post.spaceType} <br/>
        평수: ${detail.post.areaPyeong} <br/>
        스타일: ${detail.post.style} <br/>
        시공상세: ${detail.post.constructionDetail} <br/>
    </div>

    <c:if test="${not empty detail.images}">
        <div>
            <c:forEach items="${detail.images}" var="img">
                <img src="${img.url}" style="width: 200px; height: 200px;"/>
            </c:forEach>
        </div>
    </c:if>

    <c:set var="companyId" value="${detail.post.companyId}"/>
    <c:url var="backPostUrl" value="/interior/myhome/${companyId}">
        <c:param name="type" value="posts"/>
        <%--<c:param name="posts" value="${detail.post.companyPostId}"/>--%>
    </c:url>

    <a href="${backPostUrl}">뒤로가기</a>

    <c:if test="${isOwner}">
        <c:url var="editUrl" value="/interior/posts/${detail.post.companyPostId}/edit"/>
        <a href="${editUrl}">수정하기</a>
    </c:if>

    <c:if test="${isOwner}">
        <form action="<c:url value='/interior/posts/${detail.post.companyPostId}/delete'/>"
              method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="companyId" value="${detail.post.companyId}"/>
            <button type="submit">삭제하기</button>
        </form>
    </c:if>


</body>
</html>
