<%--
  Created by IntelliJ IDEA.
  User: taejun
  Date: 2025-08-09
  Time: 오후 8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
인테리어 홈에 옴
1. 업체 리스트(랜덤)
2. 업체에 대한 리뷰 리스트(최신)
3. 업체 포스트 리스트
    - 정렬: 최신, 인기(좋아요), 지역, 랜덤
--%>
<h2>인테리어 업체</h2>
<c:forEach var="c" items="${companyList}">
    <c:if test="${not empty c.thumbnailPath}">
        <c:url value="${c.thumbnailPath}" var="imgUrl"/>
        <img src="${imgUrl}" alt="${c.companyName}" style="width: 200px; height: 200px;">
    </c:if>

    <div>${c.companyName}</div>

    <div>
        <c:if test="${not empty c.companyRate}">
            별점: ${c.companyRate}
        </c:if>
        리뷰: ${c.reviewCount}
    </div>
</c:forEach>

<h2>최신 리뷰 리스트</h2>
<c:forEach var="r" items="${reviewList}">
    <div style="display: inline-block; text-align: center; width: 200px;">

    <%-- 추후 실패시 기본 이미지 추가 --%>
    <c:choose>
        <c:when test="${not empty r.thumbnail}">
        <img src="/upload/interior_review/${r.thumbnail.file_name}"
             style="display: block; width: 100px; height: 100px;">
        </c:when>
    </c:choose>

    <c:url var="detailUrl" value="/interior/review-detail">
        <c:param name="reviewId" value="${r.reviewId}"/>
    </c:url>
    <a href="${detailUrl}">
        ${r.structureType} : ${r.areaPyeong}
    </a>
    </div>
</c:forEach>

</body>
</html>
