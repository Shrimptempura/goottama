$(document).ready(function() {
	const objDragAndDrop = $(".dragAndDropDiv");

	objDragAndDrop.on('click', function() {
		$('#hiddenFileInput').trigger('click');
	});

	$('#hiddenFileInput').on('change', function(e) {
		const files = e.target.files;
		handleFileUpload(files, objDragAndDrop);
	});

	$(document).on("dragenter dragover drop", function(e) {
		e.preventDefault();
		e.stopPropagation();
	});

	objDragAndDrop.on("drop", function(e) {
		$(this).css('border', '2px dotted #0B85A1');
		const files = e.originalEvent.dataTransfer.files;
		handleFileUpload(files, objDragAndDrop);
	});

	function handleFileUpload(files, obj) {
		for (let i = 0; i < files.length; i++) {
			const fd = new FormData();
			fd.append('file', files[i]);

			const status = new createStatusbar(obj);
			status.setFileNameSize(files[i].name, files[i].size);
			sendFileToServer(fd, status);
		}
	}

	let rowCount = 0;

	function createStatusbar(obj) {
		rowCount++;
		const row = (rowCount % 2 === 0) ? "even" : "odd";
		this.statusbar = $("<div class='statusbar " + row + "'></div>");
		this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
		this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
		this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
		this.abort = $("<div class='abort'>중지</div>").appendTo(this.statusbar);
		obj.after(this.statusbar);

		this.setFileNameSize = function(name, size) {
			const sizeKB = size / 1024;
			const sizeStr = (sizeKB > 1024) ? (sizeKB / 1024).toFixed(2) + " MB" : sizeKB.toFixed(2) + " KB";
			this.filename.html(name);
			this.size.html(sizeStr);
		};

		this.setProgress = function(progress) {
			const progressBarWidth = progress * this.progressBar.width() / 100;
			this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
			if (parseInt(progress) >= 100) this.abort.hide();
		};

		this.setAbort = function(jqxhr) {
			const sb = this.statusbar;
			this.abort.click(function() {
				jqxhr.abort();
				sb.hide();
			});
		};
	}

	function sendFileToServer(formData, status) {
		const uploadURL = "/file/upload";
		const jqXHR = $.ajax({
			xhr: function() {
				const xhrobj = $.ajaxSettings.xhr();
				if (xhrobj.upload) {
					xhrobj.upload.addEventListener('progress', function(event) {
						if (event.lengthComputable) {
							const percent = Math.ceil((event.loaded / event.total) * 100);
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

				// 이미지 미리보기
				if (data && typeof data === 'string' && data.startsWith('/')) {
					const img = document.createElement("img");
					img.src = data;
					img.alt = "업로드된 이미지";
					img.style.maxWidth = "100px";
					img.style.maxHeight = "100px";
					img.style.border = "1px solid #ccc";
					img.style.marginTop = "10px";
					img.style.objectFit = "cover";

					document.getElementById("preview").appendChild(img);
				} else {
					console.warn("Invalid image path:", data);
				}
			}
		});

		status.setAbort(jqXHR);
	}
});
