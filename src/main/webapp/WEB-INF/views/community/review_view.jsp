<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

	<h4>리뷰</h4>

	<!-- 글쓰기 view단 -->
	<div class="container mt-3 d-flex justify-content-end">
		<a href="write_view" class="btn fw-bold"
			onmouseover="this.style.backgroundColor='#f0f0f0'"
			onmouseout="this.style.backgroundColor='white'">글쓰기</a>

	</div>

	<table class="table table-bordered" style="width: 100%;">
		<tr>
			<th>제목</th>
			<td>${review_view.post_title}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${review_view.post_content}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${review_view.post_date}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${review_view.post_count}</td>
		</tr>
		<tr>
			<th>좋아요 수</th>
			<td>${review_view.post_like_count}</td>
		</tr>
		<tr>
			<th>사진</th>
			<td><c:if test="${not empty review_view.post_img}">
					<img src="${review_view.post_img}" width="100">
				</c:if></td>
		</tr>
	</table>



</body>
</html>