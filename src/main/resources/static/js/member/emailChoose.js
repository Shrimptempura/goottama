/*select 옵션 이메일 도메인 선택시 input에 자동 입력*/
	document.addEventListener("DOMContentLoaded",function() {
				const domainSelect = document.getElementById("domainSelect");
				const domainInput = document.getElementById("emailDomain");

				domainSelect.addEventListener("change", function() {
					const selected = this.value;

					if (selected === "직접입력" || selected === "") {
						domainInput.value = "";
						domainInput.readOnly = false;
						domainInput.focus();
					} else {
						domainInput.value = selected;
						domainInput.readOnly = true;
					}
				});
			});
