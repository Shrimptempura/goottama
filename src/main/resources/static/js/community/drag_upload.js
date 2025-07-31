$(document).ready(function() {
	const objDragAndDrop = $(".dragAndDropDiv");
	const fileInput = $('#hiddenFileInput');
	const preview = $("#preview");

	// 클릭 시 파일 선택 창 열기
	objDragAndDrop.on('click', () => fileInput.trigger('click'));

	// 파일 선택 시
	fileInput.on('change', function(e) {
		handleFileUpload(e.target.files);
	});

	// 드래그 기본 동작 방지
	$(document).on("dragenter dragover drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
	});

	// 드롭 시
	objDragAndDrop.on("drop", function(e) {
		$(this).css('border', '2px dotted #0B85A1');
		handleFileUpload(e.originalEvent.dataTransfer.files);
	});

	// 파일 업로드 처리 함수
	function handleFileUpload(files) {
		// 미리보기 초기화
		preview.empty();

		// 상태바 초기화
		$(".statusbar").remove();

		Array.from(files).forEach(file => {
			const fd = new FormData();
			fd.append('file', file);

			const status = new createStatusbar(objDragAndDrop);
			status.setFileNameSize(file.name, file.size);
			sendFileToServer(fd, status);
		});
	}

	// 상태바 생성기
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

	// 서버로 전송 미리보기 표시
	function sendFileToServer(formData, status) {
		const targetType = "COMMUNITY_REVIEW";
		const uploadURL = `/file/upload?targetType=${targetType}`;

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

});
