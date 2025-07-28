<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티 게시판</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<h3>게시글 목록</h3>

	<div class="mb-3 d-flex justify-content-end">
		<a href="${pageContext.request.contextPath}/community/write_view"
			class="btn btn-outline-primary fw-bold"
			style="color: black; border: none;"
			onmouseover="this.style.backgroundColor='#f0f0f0'"
			onmouseout="this.style.backgroundColor='white'"> 글쓰기 </a>
	</div>



	<table class="table table-hover text-center">
		<thead class="table-light">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>사진</th>
				<th>조회수</th>
				<th>좋아요</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="review" items="${reviewList}" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a
						href="${pageContext.request.contextPath}/community/post_detail_view?post_id=${review.post_id}">
							${review.post_title} </a></td>
					<td><fmt:formatDate value="${review.post_date}"
							pattern="yyyy-MM-dd HH:mm" /></td>
					<td><c:if test="${not empty review.post_img}">
							<img
								src="${pageContext.request.contextPath}/images/${review.post_img}"
								width="80" />
						</c:if></td>
					<td>${review.post_count}</td>
					<td>${review.post_like_count}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>

</body>
</html>
