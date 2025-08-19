<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정 게시판</title>

<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/community/drag_upload.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
	<div class="container mt-4">
		<h3>수정 게시판</h3>

		<form id="updateForm"
			action="${pageContext.request.contextPath}/community/update"
			method="post">
			<!-- 식별자  -->
			<input type="hidden" name="post_id" id="post_id"
				value="${detail.post_id}" /> <input type="hidden" name="review_id"
				id="review_id" value="${detail.review_id}" /> <input type="hidden"
				id="user_id" value="${sessionScope.loginUser.user_id}" />

			<!-- 삭제할 파일 id들  -->
			<input type="hidden" name="deleted_file_ids" id="deleted_file_ids" />

			<!-- 게시판 선택 -->
			<div class="mb-3">
				<label class="form-label">게시판 선택</label> <select name="target_type"
					id="target_type" class="form-select">
					<option value="COMMUNITY_REVIEW"
						${detail.targetType eq 'COMMUNITY_REVIEW' ? 'selected' : ''}>리뷰</option>
					<option value="COMMUNITY_HOUSEPHOTO"
						${detail.targetType eq 'COMMUNITY_HOUSEPHOTO' ? 'selected' : ''}>집
						사진</option>
					<option value="COMMUNITY_HOUSEDECORATION"
						${detail.targetType eq 'COMMUNITY_HOUSEDECORATION' ? 'selected' : ''}>집
						꾸미기</option>
				</select>
			</div>

			<!-- 제목 -->
			<div class="mb-3">
				<label for="title" class="form-label">제목</label> <input type="text"
					id="title" name="review_title" value="${detail.review_title}"
					class="form-control" required />
			</div>

			<!-- 내용 -->
			<div class="mb-3">
				<label for="content" class="form-label">내용</label>
				<textarea id="content" name="review_content" rows="10"
					class="form-control" required>${detail.review_content}</textarea>
			</div>

			<!-- 새 이미지 업로드 -->
			<div class="mb-3">
				<label class="form-label">사진 추가</label> <input type="file"
					id="hiddenFileInput" style="display: none" multiple
					accept="image/*" />
				<div class="dragAndDropDiv">드래그 또는 클릭하여 파일을 선택해주세요</div>
				<div id="preview" class="mt-2 d-flex gap-2 flex-wrap"></div>
			</div>

			<!-- 기존 이미지 (삭제 가능) -->
			<div class="mb-3">
				<label class="form-label">기존 이미지</label>
				<div id="existingList" class="d-flex gap-2 flex-wrap">
					<c:forEach var="file" items="${fileList}">
						<!-- file_id, file_path 존재 가정 -->
						<div class="position-relative border rounded p-1"
							data-file-id="${file.file_id}">
							<img src="${file.file_path}" alt="이미지"
								style="width: 200px; height: auto" />
							<button type="button"
								class="btn btn-sm btn-danger position-absolute top-0 end-0 m-1 remove-existing">삭제</button>
						</div>
					</c:forEach>
				</div>
			</div>

			<div class="mb-3 d-flex justify-content-end">
				<button type="submit" class="btn btn-outline-primary fw-bold">수정
					완료</button>
			</div>
		</form>
	</div>

	<!-- 외부 JS  -->
	<script src="/js/community/edit_view.js"></script>
</body>
</html>
