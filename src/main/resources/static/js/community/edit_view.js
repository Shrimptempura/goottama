document.addEventListener("DOMContentLoaded", function() {

	const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
	const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
	if (csrfToken && csrfHeader) {
		$.ajaxSetup({
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		});
	}

	// ====== 값 주입 ======
	const reviewIdEl = document.getElementById("review_id") || document.querySelector('[name="review_id"]');
	const postIdEl = document.getElementById("post_id") || document.querySelector('[name="post_id"]');
	const userIdEl = document.getElementById("user_id");

	const reviewId = reviewIdEl?.value;
	const postId = postIdEl?.value;
	const userId = userIdEl?.value;

	if (!reviewId) {
		console.warn("review_id가 비어 있습니다. target_id로 review_id를 사용하므로 반드시 필요합니다.");
	}

	const $deleted = $("#deleted_file_ids");
	const deletedSet = new Set(); // 기존 이미지 삭제 예약
	const $drop = $(".dragAndDropDiv");
	const $fileInp = $("#hiddenFileInput");
	const $preview = $("#preview");
	const $existingList = $("#existingList");
	const $updateForm = $("#updateForm");
	const $targetType = $("#target_type");

	// ====== 기존 이미지 삭제 예약 ======
	$existingList.on("click", ".remove-existing", function() {
		const box = $(this).closest("[data-file-id]");
		const fileId = box.data("file-id");
		if (!fileId) return;

		deletedSet.add(String(fileId));
		box.remove();
		$deleted.val(Array.from(deletedSet).join(","));
	});

	// ====== 드래그 앤 드롭 / 클릭 업로더 ======
	$drop.on("click", () => $fileInp.trigger("click"));

	$drop.on("dragover", (e) => {
		e.preventDefault();
		$drop.addClass("dragover");
	});

	$drop.on("dragleave", (e) => {
		e.preventDefault();
		$drop.removeClass("dragover");
	});

	$drop.on("drop", (e) => {
		e.preventDefault();
		$drop.removeClass("dragover");
		const files = e.originalEvent.dataTransfer.files;
		if (files && files.length) handleFiles(files);
	});

	$fileInp.on("change", (e) => {
		if (e.target.files && e.target.files.length) {
			handleFiles(e.target.files);
			// 같은 파일 재선택 허용
			$fileInp.val("");
		}
	});

	// ====== 미리보기 썸네일 생성 ======
	function addPreviewThumb(fileUrl, fileId) {
		const box = $(`
      <div class="position-relative border rounded p-1" data-file-id="${fileId}">
        <img src="${fileUrl}" style="width:200px;height:auto"/>
        <button type="button" class="btn btn-sm btn-outline-danger position-absolute top-0 end-0 m-1 remove-new">
          취소
        </button>
      </div>
    `);
		$preview.append(box);
	}

	// ====== 파일 업로드 처리 ======
	function handleFiles(files) {
		const targetType = $("#target_type").val(); // 'COMMUNITY_REVIEW'
		const targetId = $("#review_id").val();

		Array.from(files).forEach(file => {
			const form = new FormData();
			form.append("file", file);

			// 카멜 스네이크 둘 다 보내서 서버 어디 규약이든 받게 처리
			form.append("targetType", targetType);
			form.append("targetId", targetId);
			form.append("target_type", targetType);
			form.append("target_id", targetId);

			$.ajax({
				url: "/file/upload_image_final",
				type: "POST",
				data: form,
				processData: false,
				contentType: false,
				success: function(res) {
					const ok = res && (res.success === true || res.success === "true");
					const fileId = res.file_id ?? res.fileId;
					const filePath = res.file_path ?? res.filePath;

					if (!ok || !fileId || !filePath) {
						console.error("upload resp mismatch:", res);
						alert("업로드 응답이 다릅니다.");
						return;
					}
					addPreviewThumb(filePath, fileId);
				},
				error: function(xhr) {
					console.error("upload fail", xhr.status, xhr.responseText);
					alert("업로드 중 오류");
				}
			});
		});
	}


	// ====== 새로 업로드한 이미지 즉시 삭제 ======
	$preview.on("click", ".remove-new", function() {
		const box = $(this).closest("[data-file-id]");
		const fileId = box.data("file-id");
		if (!fileId) return;

		$.ajax({
			url: "/file/delete",
			type: "POST",
			data: { file_id: fileId },
			success: function(res) {
				box.remove();
			},
			error: function() {
				alert("삭제 중 오류");
			}
		});
	});

	// ====== 제출 직전 삭제목록 최종 동기화 ======
	$updateForm.on("submit", function() {
		$deleted.val(Array.from(deletedSet).join(","));
	});
});
