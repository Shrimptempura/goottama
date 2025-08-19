<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 코드 입력</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/checkPwcode_view.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

<div class="container">
    <h3>비밀번호 찾기</h3>

    <form action="/checkPwCode" method="post">
        <p>이메일로 받은 인증코드를 입력하세요.</p>

        <label for="inputCode">인증코드</label>
        <input type="text" name="inputCode" id="inputCode" required />

        <input type="submit" value="인증하기" />

        <c:if test="${not empty pwCode_error}">
            <p class="error">${pwCode_error}</p>
        </c:if>
    </form>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>