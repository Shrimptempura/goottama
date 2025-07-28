<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<h4 class="mb-4">게시글</h4>

		<table class="table table-bordered">
			<tr>
				<th style="width: 120px;">제목</th>
				<td>${review.post_title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><c:out value="${review.post_content}" escapeXml="false" /></td>
			</tr>
			<tr>
				<th>작성일</th>
				<td><fmt:formatDate value="${review.post_date}"
						pattern="yyyy-MM-dd HH:mm" /></td>
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
							src="${pageContext.request.contextPath}/images/${review.post_img}"
							width="300" />
					</c:if></td>
			</tr>
		</table>

		<div class="mt-3">
			<a href="${pageContext.request.contextPath}/community/review_view"
				class="btn btn-secondary">목록으로</a>
		</div>
	</div>

</body>
</html>
