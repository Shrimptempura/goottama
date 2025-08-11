/*api 우편번호 찾기/자동입력*/

	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 선택된 우편번호와 주소 정보를 입력창에 자동 채움
				document.getElementById("zipcode").value = data.zonecode; // 우편번호
				document.getElementById("addr").value = data.roadAddress; // 도로명 주소
			}
		}).open();
	}
