<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>


<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/community/drag_upload.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<script src="/js/community/drag_upload.js"></script>

<body>
	<div class="container mt-4">
		<h3>글쓰기</h3>
		<form action="/community/write" method="post"
			enctype="multipart/form-data">

			<div class="mb-3 d-flex justify-content-end">
				<button type="submit" class="btn btn-outline-primary fw-bold"
					style="color: black; border: none; background-color: white;"
					onmouseover="this.style.backgroundColor='#f0f0f0'"
					onmouseout="this.style.backgroundColor='white'">글쓰기</button>
			</div>

			<table width="100%" class="table table-bordered">
				<tr>
					<td>제목</td>
					<td><input type="text" name="title" id="post_title"
						class="form-control" /></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="content" id="post_content" rows="10"
							class="form-control"></textarea></td>
				</tr>
				<tr>
					<td>사진</td>
					<td><input type="file" name="imgFile" id="hiddenFileInput"
						style="display: none;" multiple />
						<div id="fileUpload" class="dragAndDropDiv">드래그 또는 클릭하여 파일을
							선택해주세요</div>
						<div id="preview" class="mt-2 d-flex gap-2 flex-wrap"></div></td>
				</tr>

			</table>
		</form>

		<!-- 이미지 미리보기 -->
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
		<script>
</script>
</body>
</html>
