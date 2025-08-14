<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 내 게시글 탭</title>
</head>
<body>
    <h3>company-posts</h3>

    <c:if test="${isOwner}">
        <a href="${pageContext.request.contextPath}/interior/myhome/${companyId}/posts/new">게시글 작성</a>
    </c:if>

    <c:forEach items="${posts}" var="p">
        <div style="display: flex; text-align: center; width: 200px;">
            <c:if test="${not empty p.thumbnail}">
                <img src="/upload/interior_post/${p.thumbnail.file_name}"
                style="width: 100px; height: 80px; margin: 10px; display: inline-block">
            </c:if>

            <c:url var="detailUrl" value="/interior/posts/${p.companyPostId}"/>
            <a href="${detailUrl}">
                ${p.companyPostTitle}
                ${p.companyPostCount} | ${p.scrapCount}
            </a>
        </div>
    </c:forEach>


</body>
</html>
