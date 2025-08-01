$(document).ready(function() {
	const objDragAndDrop = $(".dragAndDropDiv");
	const fileInput = $('#hiddenFileInput');
	const preview = $("#preview");
	const submitBtn = $("#submitPostBtn"); // 글쓰기 버튼
	const selectedFiles = []; // 업로드할 파일들 저장
	let postId = null;

	// 클릭하면 파일 선택창 열기
	objDragAndDrop.on('click', () => fileInput.trigger('click'));

	// 드롭 처리
	objDragAndDrop.on("drop", function(e) {
		console.log("드롭 이벤트 발생");
		e.preventDefault();
		e.stopPropagation();
		$(this).css('border', '2px dotted #0B85A1');
		handleFileUpload(e.originalEvent.dataTransfer.files);
	});

	// 파일 선택 처리
	fileInput.on('change', function(e) {
		handleFileUpload(e.target.files);
	});

	// 드래그/드롭 기본 동작 막기
	$(document).on("dragenter dragover drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
	});

	// 파일 처리 함수
	function handleFileUpload(files) {
		preview.empty();
		$(".statusbar").remove();
		selectedFiles.length = 0; // 초기화

		Array.from(files).forEach(file => {
			selectedFiles.push(file); // 나중에 업로드용
			const status = new createStatusbar(objDragAndDrop);
			status.setFileNameSize(file.name, file.size);
			status.setProgress(0);
		});
	}

	// 상태바 구성
	let rowCount = 0;
	function createStatusbar(obj) {
		rowCount++;
		const rowClass = (rowCount % 2 === 0) ? "even" : "odd";

		this.statusbar = $(`<div class='statusbar ${rowClass}'></div>`).insertAfter(obj);
		this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
		this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
		this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
		this.abort = $("<div class='abort'>중지</div>").appendTo(this.statusbar);

		this.setFileNameSize = function(name, size) {
			const sizeKB = size / 1024;
			const sizeStr = (sizeKB > 1024 ? (sizeKB / 1024).toFixed(2) + " MB" : sizeKB.toFixed(2) + " KB");
			this.filename.text(name);
			this.size.text(sizeStr);
		};

		this.setProgress = function(progress) {
			const width = progress * this.progressBar.width() / 100;
			this.progressBar.find('div').animate({ width }, 10).html(progress + "% ");
			if (parseInt(progress) >= 100) this.abort.hide();
		};

		this.setAbort = function(jqxhr) {
			const bar = this.statusbar;
			this.abort.click(() => {
				jqxhr.abort();
				bar.hide();
			});
		};
	}

	// 실제 서버에 전송
	function sendFileToServer(formData, status) {
		const uploadURL = `/file/upload_image`;

		const jqXHR = $.ajax({
			xhr: function() {
				const xhrobj = $.ajaxSettings.xhr();
				if (xhrobj.upload) {
					xhrobj.upload.addEventListener('progress', function(e) {
						if (e.lengthComputable) {
							const percent = Math.ceil((e.loaded / e.total) * 100);
							status.setProgress(percent);
						}
					}, false);
				}
				return xhrobj;
			},
			url: uploadURL,
			type: "POST",
			contentType: false,
			processData: false,
			cache: false,
			data: formData,
			success: function(data) {
				status.setProgress(100);
				if (typeof data === 'string' && data.startsWith('/')) {
					const img = document.createElement("img");
					img.src = data;
					img.alt = "업로드된 이미지";

					Object.assign(img.style, {
						maxWidth: "100px",
						maxHeight: "100px",
						border: "1px solid #ccc",
						marginTop: "10px",
						objectFit: "cover"
					});

					img.onerror = function() {
						console.warn("이미지 로드 실패:", img.src);
						img.remove();
					};

					preview.append(img);
				} else {
					console.warn("서버에서 잘못된 이미지 경로 응답:", data);
				}
			}
		});

		status.setAbort(jqXHR);
	}

	// 글쓰기 버튼 클릭 시 전체 흐름 실행
	submitBtn.on("click", function() {
		const title = $("#title").val();
		const content = $("#content").val();
		const targetType = $("select[name='target_type']").val();

		if (!title || !content) {
			alert("제목과 내용을 입력해주세요.");
			return;
		}

		const postDto = {
			post_title: title,
			post_content: content,
			targetType: targetType
		};

		// 1. 게시글 저장
		fetch("/community/write", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(postDto)
		})
			.then(res => res.json())
			.then(postIdResult => {
				postId = postIdResult;
				console.log("작성된 post_id:", postId);

				// 2. 이미지 업로드
				const uploadPromises = selectedFiles.map(file => {
					const fd = new FormData();
					fd.append("file", file);
					fd.append("target_type", targetType);
					fd.append("post_id", postId);
					const status = new createStatusbar(objDragAndDrop);
					status.setFileNameSize(file.name, file.size);
					sendFileToServer(fd, status);
					return new Promise(resolve => setTimeout(resolve, 300)); // 순차 처리용 딜레이
				});

				// 3. 업로드 완료 후 페이지 이동
				return Promise.all(uploadPromises);
			})
			.then(() => {
				window.location.href = "/community/review_view";
			})
			.catch(error => {
				console.error("오류 발생:", error);
				alert("글 작성 또는 이미지 업로드 중 오류가 발생했습니다.");
			});
	});
});
