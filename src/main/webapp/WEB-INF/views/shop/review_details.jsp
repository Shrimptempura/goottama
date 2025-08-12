<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>베스트 상품</title>


</head>
<body>

   <h2>review_detail</h2>


	<c:forEach var="review" items="${review }">
		리뷰 아이디:${review.review_id } <br />
		리뷰 유저 닉네임:${review.user_nickname } <br />
		리뷰 상품 아이디:${review.product_id } <br />
		리뷰 상품 이름: ${review.product_name } <br />
		<br />
	</c:forEach>
</html>