<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 인증 안내</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/emailSent.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

  <div class="container">
    <div class="icon">📧</div>
    <h1>이메일 인증 안내</h1>
    <p>
      회원가입을 완료하려면<br>
      가입 시 입력한 이메일 주소로 발송된<br>
      <span class="highlight">인증 메일</span>을 확인해주세요.
    </p>
    <p>
      메일 안에 있는 <span class="highlight">인증 링크</span>를 클릭하면<br>
      회원가입이 완료됩니다.
    </p>
  </div>
  
 <%@ include file="../common/footer.jsp" %>
</body>
</html>