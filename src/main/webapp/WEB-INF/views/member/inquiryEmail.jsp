<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 문의</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/inquiryEmail.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

<div class="inquiry-container">
    <h3>이메일 문의하기</h3>

    <form action="/sendInquiry" method="post">
        <div class="form-group">
            <label for="subject">제목</label>
            <input type="text" id="subject" name="subject" required />
        </div>

        <div class="form-group">
            <label>이메일</label>
            <div class="readonly-email">${memberDto.user_email}</div>
        </div>

        <div class="form-group">
            <label for="message">내용</label>
            <textarea id="message" name="message" required></textarea>
        </div>

        <input type="submit" value="보내기" class="submit-btn" />
    </form>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>