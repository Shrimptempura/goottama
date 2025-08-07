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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/community/review_view.css">

</head>
<body class="container mt-4">

	<h3>리뷰 게시글 목록</h3>

	<div class="mb-3 d-flex justify-content-end">
		<a href="${pageContext.request.contextPath}/community/write_view"
			class="btn btn-outline-primary fw-bold write-button">글작성</a>
	</div>

	<!-- 게시글 리스트 -->
	<c:forEach var="post" items="${reviewList}">
		<div class="post-card">
			<div class="post-content">
				<div class="title">
					<a
						href="${pageContext.request.contextPath}/community/post_detail_view?post_id=${post.post_id}">
						${post.review_title} </a>
				</div>

				<div class="summary">${post.review_content}</div>

				<div class="meta">
					<span class="date"> <fmt:formatDate
							value="${post.review_date}" pattern="yyyy.MM.dd" /> <span
						class="views"> <span class="meta-label">조회</span> <span
							id="review_count_${post.review_id}">${post.review_count}</span>
					</span> <span class="likes"> <span class="meta-label">좋아요</span> <span
							id="review_like_count_${post.review_id}">${post.review_like_count}</span>
					</span>
				</div>

			</div>

			<c:choose>
				<c:when test="${not empty post.fileList}">
					<div class="thumbnail">
						<img src="${post.fileList[0].file_path}" alt="썸네일" />
					</div>
				</c:when>
				<c:otherwise>
					<div class="thumbnail">
						<img src="/img/no-image.png" alt="이미지 없음" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</c:forEach>

	<!-- 페이지네이션 -->
	<div class="d-flex justify-content-center mt-4">
		<nav>
			<ul class="pagination">
				<c:if test="${pageVO.pageStart > 1}">
					<li class="page-item"><a class="page-link"
						href="?page=${pageVO.pageStart - 1}">&laquo;</a></li>
				</c:if>

				<c:forEach begin="${pageVO.pageStart}" end="${pageVO.pageEnd}"
					var="i">
					<li class="page-item ${i == pageVO.page ? 'active' : ''}"><a
						class="page-link" href="?page=${i}">${i}</a></li>
				</c:forEach>

				<c:if test="${pageVO.pageEnd < pageVO.totPage}">
					<li class="page-item"><a class="page-link"
						href="?page=${pageVO.pageEnd + 1}">&raquo;</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/community/update_review_count.js"></script>


</body>
</html>
