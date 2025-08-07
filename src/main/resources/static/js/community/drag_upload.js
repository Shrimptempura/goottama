$(document).ready(function() {
	console.log("✅ drag_upload.js 로드됨");

	const objDragAndDrop = $(".dragAndDropDiv");
	const fileInput = $('#hiddenFileInput');
	const preview = $("#preview");
	const selectedFiles = [];

	// ⭐ 기존 임시 이미지 삭제 함수
	function deleteTemporaryImages() {
		fetch("/file/delete_temp", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			},
			body: new URLSearchParams({
				user_id: 1, // 실제 로그인 유저 정보로 대체
				target_type: "COMMUNITY_REVIEW"
			})
		})
			.then(res => console.log("🧹 기존 임시 이미지 삭제 요청 보냄"))
			.catch(err => console.error("❌ 임시 이미지 삭제 실패:", err));
	}

	// 클릭하면 파일 선택창 열기
	objDragAndDrop.on('click', () => {
		console.log("✅ 드래그박스 클릭됨 → 파일선택창 열림");
		fileInput.trigger('click');
	});

	// 파일 선택 시 처리
	fileInput.on('change', function(e) {
		console.log("✅ 파일 선택됨", e.target.files);
		handleFileUpload(e.target.files);
	});

	// 드래그/드롭 기본 동작 방지
	$(document).on("dragenter dragover drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
	});

	// 드롭 처리
	objDragAndDrop.on("drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
		console.log("✅ 드래그된 파일 drop됨", e.originalEvent.dataTransfer.files);
		handleFileUpload(e.originalEvent.dataTransfer.files);
	});

	// 파일 업로드 처리 함수
	function handleFileUpload(files) {
		console.log("📂 handleFileUpload 실행됨", files);

		// ⭐ 기존 임시 파일 서버에서 삭제
		deleteTemporaryImages();

		// 프리뷰와 선택 파일 초기화
		preview.empty();
		selectedFiles.length = 0;

		Array.from(files).forEach(file => {
			selectedFiles.push(file);
			console.log("📥 파일 추가됨:", file.name);

			// 이미지 업로드 즉시 실행
			const formData = new FormData();
			formData.append("file", file);
			formData.append("target_type", "COMMUNITY_REVIEW");
			formData.append("user_id", 1); // 백엔드용

			fetch("/file/upload_image", {
				method: "POST",
				body: formData
			})
				.then(response => response.text())
				.then(result => {
					console.log("📤 즉시 업로드 성공:", file.name, result);
				})
				.catch(err => {
					console.error("❌ 즉시 업로드 실패:", file.name, err);
				});

			// 이미지 미리보기
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
			} else {
				console.warn("🚫 이미지 파일이 아님:", file.name);
			}
		});
	}

	// 글쓰기 버튼 클릭 시
	$("#submitPostBtn").on("click", function() {
		const reviewTitle = $("#title").val();
		const reviewContent = $("#content").val();
		const targetType = $("select[name='target_type']").val();

		if (!reviewTitle.trim() || !reviewContent.trim()) {
			alert("제목과 내용을 모두 입력해주세요.");
			return;
		}

		console.log("📝 글 작성 요청 시작");
		fetch("/community/write", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			},
			body: new URLSearchParams({
				review_title: reviewTitle,
				review_content: reviewContent,
				target_type: targetType
				// user_id는 백엔드에서 1L로 고정
			})
		})
			.then(response => response.json())
			.then(data => {
				const postId = data.post_id;
				console.log("✅ 글 작성 성공 post_id:", postId);
				window.location.href = "/community/review_view";
			})
			.catch(error => {
				console.error("❌ 글 작성 실패:", error);
				alert("서버와 통신 중 오류가 발생했습니다.");
			});
	});

	// 이미지 업로드 (글쓰기 완료 후 업로드용)
	function uploadFiles(postId, targetType) {
		selectedFiles.forEach(file => {
			const formData = new FormData();
			formData.append("file", file);
			formData.append("target_type", targetType);
			formData.append("post_id", postId);
			formData.append("user_id", 1); // 백엔드에서 이미 고정되어 있어도 서버 로그 확인용

			fetch("/file/upload_image", {
				method: "POST",
				body: formData
			})
				.then(response => response.text())
				.then(result => {
					console.log("📤 파일 업로드 성공:", file.name, result);
				})
				.catch(err => {
					console.error("❌ 파일 업로드 실패:", file.name, err);
				});
		});
	}
});
