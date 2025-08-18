<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>집사진 게시판</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/community/house_photo_view.css">
</head>

<body class="container mt-4">

	<h3>집사진 게시글 목록</h3>

	<div class="mb-3 d-flex justify-content-end">
		<a href="${pageContext.request.contextPath}/community/write_view"
			class="btn btn-outline-primary fw-bold write-button">글작성</a>
	</div>

	<!-- 게시글 리스트 -->
	<c:forEach var="post" items="${housePhotoList}">
		<div
			class="post-card d-flex justify-content-between align-items-stretch border rounded p-3 mb-3">
			<div class="post-content flex-grow-1 me-3">
				<div class="title h5 mb-2">
					<a
						href="${pageContext.request.contextPath}/community/post_detail_view?post_id=${post.post_id}">
						${post.housephoto_title} </a>
				</div>

				<div class="summary text-muted mb-2">
					${post.housephoto_content}</div>

				<div class="meta small text-secondary">
					<span class="date me-3"> <fmt:formatDate
							value="${post.review_date}" pattern="yyyy.MM.dd" />
					</span> <span class="views me-3"> <span class="meta-label">조회</span>
						<span id="housephoto_count_${post.housephoto_id}">${post.housephoto_count}</span>
					</span> <span class="likes me-3"> <span class="meta-label">좋아요</span>
						<span id="housephoto_like_count_${post.housephoto_id}">${post.housephoto_like_count}</span>
					</span> <span class="comments"> <span class="meta-label">댓글</span>
						<span id="housephoto_comment_count_${post.housephoto_id}">
							${post.comment_count} </span>
					</span>
				</div>
			</div>

			<c:choose>
				<c:when test="${not empty post.fileList}">
					<div class="thumbnail"
						style="width: 180px; height: 120px; overflow: hidden;">
						<img src="${post.fileList[0].file_path}" alt="썸네일"
							style="width: 100%; height: 100%; object-fit: cover;" />
					</div>
				</c:when>
				<c:otherwise>
					<div
						class="thumbnail d-flex align-items-center justify-content-center bg-light"
						style="width: 180px; height: 120px;">
						<img src="/img/no-image.png" alt="이미지 없음"
							style="max-width: 100%; max-height: 100%;" />
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
		src="${pageContext.request.contextPath}/js/community/update_housephoto_count.js"></script>
</body>
</html>
