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

	<table width="500" border="1">
		<tr>
			<td>제목</td>
			<td>내용</td>
			<td>프로필</td>
			<td>작성자</td>
			<td>조회수</td>
			<td>댓글 수</td>
			<td>좋아요 수</td>
			<td>사진</td>
		</tr>
		<c:forEach items="${review_view }" var="dto">
			<tr>
				<td>${dto.title }</td>

			</tr>
		</c:forEach>

	</table>

</body>
</html>