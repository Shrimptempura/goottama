<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 5:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 메인 화면</title>
</head>
<body>
<h3>company-all</h3>
<%-- 좌측 요약 상자 --%>
<div style="display: flex;">
    <div style="width: 15%; position: fixed; padding-right: 10px; top: 70px; background-color: #f5f5f5; margin-left: 10px;">
        <jsp:include page="/WEB-INF/views/interior/fragments/company-summary.jsp" />
    </div>

    <%-- 본문 --%>
    <div style="margin-left: 18%; padding: 20px; background-color: #ffeaa7; width: 60%;">
        <%-- 탭 부분 --%>
        <ul style="list-style: none; display: flex; gap: 15px; text-decoration: none;">
            <li><a href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=all">모두보기</a></li>
            <li><a href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=reviews">리뷰</a></li>
            <li><a href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=posts">게시글</a></li>
            <li><a href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=photos">사진</a></li>
            <li><a href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=details">상세정보</a></li>
        </ul>
        <hr/>

        <%-- 탭 내용 --%>
        <jsp:include page="${tabName}"/>
    </div>
</div>
</body>
</html>