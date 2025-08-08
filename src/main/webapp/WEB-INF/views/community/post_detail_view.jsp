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

		<div class="mt-5">
			<h5>댓글</h5>

			<!-- 댓글 작성 폼 -->
			<form method="post"
				action="${pageContext.request.contextPath}/community/comment"
				class="mb-4">
				<input type="hidden" name="targetId" value="${review.post_id}" /> <input
					type="hidden" name="targetType" value="COMMUNITY_REVIEW" /> <input
					type="hidden" name="user_id"
					value="${sessionScope.loginUser.user_id}" />

				<div class="mb-2">
					<textarea name="comment_content" class="form-control" rows="3"
						placeholder="댓글을 입력하세요" required></textarea>
				</div>
				<div class="text-end">
					<button type="submit" class="btn btn-primary btn-sm">댓글 작성</button>
				</div>
			</form>

			<!-- 댓글 목록 -->
			<c:if test="${not empty commentList}">
				<ul class="list-group">
					<c:forEach var="comment" items="${commentList}">
						<li class="list-group-item">
							<div>
								<strong>작성자 ID:</strong> ${comment.user_id}
							</div>
							<textarea name="comment_content_${comment.comment_id}"
								class="form-control my-2" rows="2"
								form="form-${comment.comment_id}">${comment.comment_content}</textarea>

							<div class="d-flex justify-content-between">
								<small class="text-muted"> 작성일: <fmt:formatDate
										value="${comment.created_at}" pattern="yyyy-MM-dd HH:mm" />
								</small>

								<div>
									<!-- 수정 -->
									<form id="form-${comment.comment_id}" method="post"
										action="${pageContext.request.contextPath}/community/comment/update"
										class="d-inline">
										<input type="hidden" name="comment_id"
											value="${comment.comment_id}" /> <input type="hidden"
											name="targetId" value="${review.post_id}" /> <input
											type="hidden" name="targetType" value="COMMUNITY_REVIEW" />
										<input type="hidden" name="user_id" value="${comment.user_id}" />
										<input type="hidden" name="comment_content" value="" />
										<button type="submit" class="btn btn-sm btn-success"
											onclick="
                                    this.form.comment_content.value = this.form.closest('li').querySelector('textarea').value;
                                ">수정</button>
									</form>

									<!-- 삭제 -->
									<form method="post"
										action="${pageContext.request.contextPath}/community/comment/delete"
										class="d-inline" onsubmit="return confirm('댓글을 삭제하시겠습니까?');">
										<input type="hidden" name="commentId"
											value="${comment.comment_id}" /> <input type="hidden"
											name="postId" value="${review.post_id}" />
										<button type="submit" class="btn btn-sm btn-outline-danger">삭제</button>
									</form>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:if>

			<c:if test="${empty commentList}">
				<p class="text-muted">댓글이 없습니다.</p>
			</c:if>
		</div>


		<script>
	const contextPath = "${pageContext.request.contextPath}";
	const postId = '${review.post_id}';
</script>

		<script
			src="${pageContext.request.contextPath}/js/community/like_button.js"></script>
</body>
</html>
