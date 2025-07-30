<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<a href="/mypage/myProfile"> 프로필 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/myOrderList">나의쇼핑</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/myReview">나의활동</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/editProfile_view"><strong>설정</strong></a> <br />

<a href="/mypage/editProfile_view">회원정보변경</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/editPassword"><strong>비밀번호변경</strong></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/customerCenter">고객센터</a> <br />

비밀번호 변경
<form action="/findPw" method="post" >
아이디 : <input type="text" name="loginId" />
이메일 : <input type="text" name="email" />
<input type="submit" value="인증 이메일 보내기" />
</form>


</body>
</html>