<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/community/detail_color.css" />

</head>
<body>

	<div class="container mt-4">
		<h4 class="mb-4">게시글</h4>

		<div class="mt-3 d-flex justify-content-between">
			<a href="${pageContext.request.contextPath}/community/review_view"
				class="btn btn-white list">목록</a>

			<div class="d-flex gap-2">
				<a
					href="${pageContext.request.contextPath}/community/edit?post_id=${review.post_id}"
					class="btn btn-white edit">수정</a>

				<form action="${pageContext.request.contextPath}/community/delete"
					method="post" onsubmit="return confirm('정말 삭제하시겠습니까?')"
					class="d-inline">
					<input type="hidden" name="post_id" value="${review.post_id}" />
					<button type="submit" class="btn btn-white delete">삭제</button>
				</form>
			</div>
		</div>

		<!-- 여백 만들기 -->
		<span style="margin-left: 12px;"></span>




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
	</div>

</body>
</html>
