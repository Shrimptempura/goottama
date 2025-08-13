<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정 게시판</title>
</head>
<body>
	<h3>수정 게시판</h3>

	<form action="${pageContext.request.contextPath}/community/update"
		method="post">
		<input type="hidden" name="post_id" value="${detail.post_id}" /> <input
			type="hidden" name="review_id" value="${detail.review_id}" />

		<div class="mb-3">
			<label for="title" class="form-label">제목</label> <input type="text"
				id="title" name="review_title" value="${detail.review_title}"
				class="form-control" />
		</div>

		<div class="mb-3">
			<label for="content" class="form-label">내용</label>
			<textarea id="content" name="review_content" class="form-control"
				rows="8">${detail.review_content}</textarea>
		</div>

		<div class="mb-3 d-flex justify-content-end">
			<button type="submit" class="btn btn-primary"
				style="color: black; border: none; background-color: white;"
				onmouseover="this.style.backgroundColor='#f0f0f0'"
				onmouseout="this.style.backgroundColor='white'">수정 완료</button>
		</div>
	</form>

</body>
</html>
