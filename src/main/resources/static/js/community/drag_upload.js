$(document).ready(function() {
	console.log("drag_upload.js 로드됨@@@@@@@@@@@@@@@@@@@@@@@@");

	const objDragAndDrop = $(".dragAndDropDiv");
	const fileInput = $('#hiddenFileInput');
	const preview = $("#preview");
	const selectedFiles = [];

	// 세션 동안 유지할 임시 음수 ID
	let TEMP_ID = -Date.now(); // 한 글쓰기 세션에 하나

	// 임시 이미지 삭제
	function deleteTemporaryImages() {
		fetch("/file/delete_temp", {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: new URLSearchParams({
				target_type: "COMMUNITY_REVIEW",
				keep_temp_id: TEMP_ID
			})
		})
			.then(() => console.log("기존 이미지 정리 완료(현재 TEMP_ID 보존)@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"))
			.catch(err => console.error("임시 이미지 삭제 실패@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:", err));
	}

	objDragAndDrop.on('click', () => fileInput.trigger('click'));

	fileInput.on('change', function(e) {
		handleFileUpload(e.target.files);
	});

	$(document).on("dragenter dragover drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
	});

	objDragAndDrop.on("drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
		handleFileUpload(e.originalEvent.dataTransfer.files);
	});

	function handleFileUpload(files) {
		deleteTemporaryImages();          // 예전 음수 묶음 정리
		preview.empty();
		selectedFiles.length = 0;

		Array.from(files).forEach(file => {
			selectedFiles.push(file);
			
			// 파일 업로드
			const formData = new FormData();
			formData.append("file", file);
			formData.append("target_type", "COMMUNITY_REVIEW");
			formData.append("temp_id", TEMP_ID);     // 음수 임시 ID 묶음으로 업로드

			fetch("/file/upload_image", { method: "POST", body: formData })
				.then(r => r.text())
				.then(tempOrId => {
					console.log("임시 업로드 OK. server returned@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:", tempOrId);
				})
				.catch(err => console.error("업로드 실패@@@@@@@@@@@@@@@@@@@@@@@@@@:", file.name, err));

			if (file.type.startsWith("image/")) {
				const reader = new FileReader();
				reader.onload = function(e) {
					const img = document.createElement("img");
					img.src = e.target.result;
					img.alt = "미리보기";
					Object.assign(img.style, {
						maxWidth: "100px",
						maxHeight: "100px",
						border: "1px solid #ccc",
						marginTop: "10px",
						objectFit: "cover"
					});
					preview.append(img);
				};
				reader.readAsDataURL(file);
			}
		});
	}

	// 글쓰기 버튼
	$("#submitPostBtn").on("click", function() {
		const reviewTitle = $("#title").val();
		const reviewContent = $("#content").val();
		const targetType = $("select[name='target_type']").val();

		if (!reviewTitle.trim() || !reviewContent.trim()) {
			alert("제목과 내용을 모두 입력해주세요.");
			return;
		}

		fetch("/community/write", {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: new URLSearchParams({
				review_title: reviewTitle,
				review_content: reviewContent,
				target_type: targetType
			})
		})
			.then(r => r.json())
			.then(async data => {
				const postId = data.post_id;
				console.log("글 작성 성공 post_id@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:", postId);

				// 임시 postId로 확정
				const confirmParams = new URLSearchParams({
					target_type: targetType,
					temp_id: TEMP_ID,  // 업로드 때 썼던 음수 ID
					target_id: postId  // 실제 게시글 ID
				});

				const res = await fetch("/file/confirm", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: confirmParams
				});

				if (!res.ok) throw new Error("파일 확정 실패");
				console.log("임시 파일 → 게시글로 매핑 완료@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

				window.location.href = "/community/review_view";
			})
			.catch(err => {
				console.error("글 작성/파일 확정 실패@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:", err);
				alert("오류가 발생했습니다.");
			});
	});
});
