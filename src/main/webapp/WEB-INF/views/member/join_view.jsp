<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="join" method="post">
아이디: <input type="text" name="loginId" /> <br />
pw: <input type="text" name="pw" /> <br />
권한: <input type="radio" name="roles" value="1"  /> 관리자
<input type="radio" name="roles" value="2"  /> 일반회원
<input type="radio" name="roles" value="3"  /> 업체 <br />
이름: <input type="text" name="name" /> <br />
닉네임: <input type="text" name="nickname" /> <br />
성별: <input type="radio" name="gender" value="M" />남 <input type="radio" name="gender" value="F" />여 <br />
생년월일: <input type="text" name="birth"/> <br />
연락처: <input type="text" name="tel"/> <br />
우편번호: <input type="text" name="zipcode"/> <br />
상세주소: <input type="text" name="addr"/> <br />
이메일: <input type="text" name="email"/> <br />
<input type="submit" value="join" />
</form>

</body>
</html>