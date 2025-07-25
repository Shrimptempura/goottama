<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

<!-- 기존 부트스트랩 CDN -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<!-- Summernote CSS 추가 -->
<link rel="stylesheet"
	href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/webjars/summernote/0.8.20/summernote-bs5.css">

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
				<td>
					<!-- Summernote 에디터 적용 --> <textarea id="summernote" name="content"></textarea>
				</td>
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

	<!-- Summernote JS 추가 -->
	<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
	<script src="/webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	<script src="/webjars/summernote/0.8.20/summernote-bs5.min.js"></script>

	<script>
        $(document).ready(function () {
            $('#summernote').summernote({
                height: 300,
                callbacks: {
                    onImageUpload: function (files) {
                        let data = new FormData();
                        data.append("imgFile", files[0]);
                        $.ajax({
                            url: '/upload-image',
                            method: 'POST',
                            data: data,
                            contentType: false,
                            processData: false,
                            success: function (url) {
                                $('#summernote').summernote('insertImage', url);
                            },
                            error: function () {
                                alert("이미지 업로드 실패");
                            }
                        });
                    }
                }
            });
        });
    </script>
</body>
</html>
