<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천 게시글</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/community/suggest.css">

</head>
<body class="container mt-4">

	<h3>추천 게시글</h3>

	<c:forEach var="post" items="${popularList}">
		<div class="card mb-3">
			<div class="card-body">
				<h5 class="card-title">
					<a
						href="${pageContext.request.contextPath}/community/post_detail_view?post_id=${post.post_id}">
						${post.review_title} </a>
				</h5>
				<p class="card-text">${post.review_content}</p>
				<p class="card-text">
					<span class="badge bg-success">좋아요 ${post.review_like_count}</span>
					<span class="badge bg-info">댓글 ${post.comment_count}</span>
				</p>
			</div>
		</div>
	</c:forEach>


</body>
</html>
