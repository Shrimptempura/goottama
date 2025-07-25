<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
#submitBtn {
	background-color: white;
	font-weight: bold;
	border: none;
	cursor: pointer;
}

#submitBtn:hover {
	background-color: #f0f0f0;
}
</style>
</head>
<body>
	<h3>글쓰기</h3>
	<form action="/write" method="post" enctype="multipart/form-data">
		<table width="500" border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" id="post_title" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" id="post_content" cols="30"
						rows="10">글을 작성해주세요</textarea></td>
			</tr>
			<tr>
				<td>사진</td>
				<td><input type="file" name="imgFile" id="post_img"
					accept="image/*" />
					<div id="preview"
						style="margin-top: 10px; display: flex; gap: 10px; flex-wrap: wrap;"></div>
				</td>
			</tr>
		</table>

		<div class="container mt-3 d-flex justify-content-end">
			<!-- ✅ 버튼을 스타일만 a태그처럼 유지하고 실제는 submit 동작 -->
			<button type="submit" id="submitBtn" class="btn">글작성</button>
		</div>
	</form>

	<!-- 이미지 미리보기 스크립트 -->
	<script>
		document.getElementById("post_img").addEventListener("change", function (e) {
			const preview = document.getElementById("preview");
			preview.innerHTML = "";
			Array.from(e.target.files).forEach(file => {
				if (!file.type.startsWith("image/")) return;
				const reader = new FileReader();
				reader.onload = function (event) {
					const img = document.createElement("img");
					img.src = event.target.result;
					img.style.maxWidth = "100px";
					img.style.maxHeight = "100px";
					img.style.border = "1px solid #ccc";
					img.style.objectFit = "cover";
					preview.appendChild(img);
				};
				reader.readAsDataURL(file);
			});
		});
	</script>
</body>
</html>
