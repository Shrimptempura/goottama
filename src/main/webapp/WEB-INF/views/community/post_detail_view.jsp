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
					method="post" onsubmit="return confirm('삭제하시겠습니까?')"
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
				<td>${review.review_title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><c:out value="${review.review_content}" escapeXml="false" /></td>
			</tr>
			<tr>
				<th>작성일</th>
				<td><fmt:formatDate value="${review.review_date}"
						pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${review.review_count}</td>
			</tr>
			<tr>
				<th>좋아요 수</th>
				<td><span id="likeCount">${review.review_like_count}</span>
					<button type="button" class="like-heart-btn"
						onclick="likePost(${review.review_id})">♥</button></td>
			</tr>
			<tr>
				<th>사진</th>
				<td><c:if test="${not empty review.fileList}">
						<c:forEach var="img" items="${review.fileList}">
							<img src="${pageContext.request.contextPath}${img.file_path}"
								style="max-width: 300px;" />
						</c:forEach>
					</c:if> <c:if test="${empty review.fileList}">이미지 없음</c:if></td>
			</tr>

		</table>

		<form action="${pageContext.request.contextPath}/comment/create"
			method="post">
			<input type="hidden" name="targetId" value="${post.post_id}" /> <input
				type="hidden" name="targetType" value="INTERIOR" />
			<textarea name="commentContent" rows="3" class="form-control"
				required></textarea>
			<button type="submit" class="btn btn-sm btn-primary mt-2">댓글
				작성</button>
		</form>


		<h5>
			댓글 <span>${commentCount}</span>개
		</h5>

		<div class="comment-section">
			<c:forEach var="comment" items="${commentList}">
				<div class="comment-box">
					<div class="comment-header">
						<img src="${comment.userProfileImgPath}" width="30" height="30" />
						<strong>${comment.userNickname}</strong> <span
							class="comment-date"> <fmt:formatDate
								value="${comment.createdAt}" pattern="yyyy.MM.dd HH:mm" />
						</span>
					</div>

					<div class="comment-body">
						<c:choose>
							<c:when test="${comment.isDeleted == 1}">
								<i class="text-muted">삭제된 댓글입니다.</i>
							</c:when>
							<c:otherwise>
								<span>${comment.commentContent}</span>
							</c:otherwise>
						</c:choose>
					</div>

					<!-- 댓글 수정/삭제 버튼은 본인에게만 보여주도록 처리 -->
				</div>
			</c:forEach>
		</div>

	</div>

	<script>
	const contextPath = "${pageContext.request.contextPath}";
	const postId = '${review.post_id}';
</script>

	<script
		src="${pageContext.request.contextPath}/js/community/like_button.js"></script>
</body>
</html>
