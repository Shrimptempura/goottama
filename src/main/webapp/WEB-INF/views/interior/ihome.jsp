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

<h2>인테리어 업체</h2>
<c:forEach var="c" items="${homeList}">
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
</body>
</html>
