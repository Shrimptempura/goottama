<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

<!-- CSS & JS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/community/drag_upload.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
	<div class="container mt-4">
		<h3>글쓰기</h3>

		<div id="postForm">

			<!-- 게시판 선택 -->
			<div class="mb-3">
				<label class="form-label">게시판 선택</label> <select name="target_type"
					class="form-select">
					<option value="COMMUNITY_REVIEW">리뷰</option>
					<option value="COMMUNITY_HOUSEPHOTO">집 사진</option>
					<option value="COMMUNITY_HOUSEDECORATION">집 꾸미기</option>
				</select>
			</div>

			<!-- 제목 -->
			<div class="mb-3">
				<label for="title" class="form-label">제목</label> <input type="text"
					name="review_title" id="title" class="form-control" required />
			</div>

			<!-- 내용 -->
			<div class="mb-3">
				<label for="content" class="form-label">내용</label>
				<textarea name="review_content" id="content" rows="10"
					class="form-control" required></textarea>
			</div>

			<!-- 파일 업로드 -->
			<div class="mb-3">
				<label class="form-label">사진</label> <input type="file"
					id="hiddenFileInput" style="display: none;" multiple
					accept="image/*" />
				<div class="dragAndDropDiv">드래그 또는 클릭하여 파일을 선택해주세요</div>
				<div id="preview" class="mt-2 d-flex gap-2 flex-wrap"></div>
			</div>

			<!-- 업로드된 기존 이미지 미리보기 -->
			<div class="mb-3">
				<label class="form-label"></label>
				<div class="d-flex gap-2 flex-wrap">
					<c:forEach var="file" items="${fileList}">
						<img src="${file.file_path}" alt="업로드 이미지"
							style="width: 200px; height: auto;" />
					</c:forEach>
				</div>
			</div>

			<!-- 숨겨진 사용자 ID -->
			<input type="hidden" id="user_id"
				value="${sessionScope.loginUser.user_id}" />

			<!-- 글쓰기 버튼 -->
			<div class="mb-3 d-flex justify-content-end">
				<button type="button" id="submitPostBtn"
					class="btn btn-outline-primary fw-bold">글쓰기</button>
			</div>
		</div>
	</div>

	<script>
		const userId = document.getElementById("user_id").value;
	</script>
	<script src="/js/community/drag_upload.js"></script>
</body>
</html>
