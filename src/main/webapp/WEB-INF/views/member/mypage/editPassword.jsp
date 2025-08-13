<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/editPassword.css" />
</head>
<body>

<div class="nav">
<a href="/mypage/myProfile"> 프로필 </a> 
<a href="/mypage/myOrderList">나의쇼핑</a> 
<a href="/mypage/myComment">나의활동</a> 
<a href="/mypage/editProfile_view"><strong>설정</strong></a>
</div>

<div class="sub-nav">
<a href="/mypage/editProfile_view">회원정보변경</a> 
<a href="/mypage/editPassword"><strong>비밀번호변경</strong></a> 
<a href="/mypage/customerCenter">고객센터</a>
</div>

<div class="pw-form-container">
    <h2>비밀번호 변경</h2>
    <form action="/findPw" method="post">
      <label for="loginId">아이디</label>
      <input type="text" id="loginId" name="loginId" required />

      <label for="email">이메일</label>
      <input type="text" id="email" name="email" required />

      <input type="submit" value="인증 이메일 보내기" />
    </form>
  </div>
</form>


</body>
</html>