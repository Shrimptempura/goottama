<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/login_view.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

	<main>
	<div class="login-container">
		<h3>LogIn</h3>

		<form action="/authenticate" method="post">
			 <label for="loginId">아이디</label>
			 <input type="text" name="loginId" id="loginId" required/><br />
			
			 <label for="pw">비밀번호</label>
			 <input type="password" name="pw"  id="pw" required/><br /> 
			 
			 <c:if test="${param.error == 'true'}">
  			  <p class="error">아이디 또는 비밀번호가 올바르지 않습니다.</p>
			</c:if>
			 
			 <input type="submit" value="로그인" class="login-btn"/>
		</form>

		<div class="links">
			<a href="/findLoginId_view">아이디 찾기</a> 
			<a href="/findPw_view">비밀번호찾기</a> 
			<a href="/join_view">회원가입</a>
		</div>
	</div> 
	</main>
	
<%@ include file="../common/footer.jsp" %>
</body>
</html>