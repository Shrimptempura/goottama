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
		<thead>
			<tr>
				<th>제목</th>
				<th>내용</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>좋아요 수</th>
				<th>사진</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${review_view}" var="dto">
				<tr>
					<td>${dto.post_title}</td>
					<td>${dto.post_content}</td>
					<td>${dto.post_date}</td>
					<td>${dto.post_count}</td>
					<td>${dto.post_like_count}</td>
					<td><c:if test="${not empty dto.post_img}">
							<img src="upload/${dto.post_img}" alt="사진"
								style="width: 100px; height: auto;">
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


</body>
</html>