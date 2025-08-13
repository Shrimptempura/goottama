<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/withdrawalSuccess_view.css" />
</head>
<body>

<div class="withdrawal-success-container">
  <h2>회원 탈퇴 완료</h2>
  <p class="message">회원 탈퇴가 정상적으로 처리되었습니다.</p>
  <form action="/member/withdrawalSuccess" method="get">
    <input type="submit" class="btn-home" value="홈으로 가기" />
  </form>
</div>

</body>
</html>