<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>글쓰기</h3>
	<form action="write" method="post" enctype="multipart/form-data">
		<table width="500" border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="post_title" id="post_title"
					size="50" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" id="post_content" cols="30"
						rows="10">content</textarea></td>
			</tr>
			<tr>
				<td>사진</td>
				<td><input multiple type="file" name="post_img" id="post_img"
					accept="image/*" />
					<div id="preview"
						style="margin-top: 10px; display: flex; gap: 10px; flex-wrap: wrap;"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="글쓰기" /></td>
			</tr>
		</table>
	</form>

	<!-- 선택한 이미지 미리보기 -->
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