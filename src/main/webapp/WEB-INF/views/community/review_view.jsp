<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 상세</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

	<div class="container mt-4">

		<h4 class="mb-4">리뷰 게시글</h4>

		<div class="mb-3 d-flex justify-content-end">
			<a href="${pageContext.request.contextPath}/Community/write_view"
				class="btn btn-outline-primary fw-bold"
				onmouseover="this.style.backgroundColor='#f0f0f0'"
				onmouseout="this.style.backgroundColor='white'">글쓰기</a>

		</div>

		<table class="table table-bordered">
			<tr>
				<th style="width: 120px;">제목</th>
				<td>${review.post_title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${review.post_content}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${review.post_date}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${review.post_count}</td>
			</tr>
			<tr>
				<th>좋아요 수</th>
				<td>${review.post_like_count}</td>
			</tr>
			<tr>
				<th>사진</th>
				<td><c:if test="${not empty review.post_img}">
						<img
							src="${pageContext.request.contextPath}/upload/${review.post_img}"
							width="150" alt="게시글 이미지" />
					</c:if></td>
			</tr>
		</table>

	</div>

</body>
</html>
