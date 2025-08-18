<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>상품 문의 작성</title>

</head>
<body>
<h2>review_update_view</h2>


<div>

<form action="review_update">
	<!---->
	<!---->
	리뷰 아이디: ${review.review_id }
	<br />
		사용자 아이디: ${review.user_id }
		<br />
		리뷰 유저 닉네임: ${review.user_nickname }
	
	<br />
	
	<input type="hidden" name="target_id" value="${review.target_id }"/>
	<input type="hidden" name="review_id" value="${review.review_id }"/>
	리뷰제목: <input name="review_title" type="text" value="${review.review_title }" style="width: 300px; height: 30px"/> <br />
	리뷰내용: <input name="review_content" type="text" value="${review.review_content }" style="width: 300px; height: 200px"/>

	<br />
	<input type="submit" value="수정" />

</form>	


</div>

</body>
<%@ include file="../list.jsp" %>
</html>