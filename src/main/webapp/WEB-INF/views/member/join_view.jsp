<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 이메일 선택 --> 
<script>
	document.addEventListener("DOMContentLoaded",
			function() {
				const domainSelect = document
						.querySelector("select[name='domainSelect']");
				const domainInput = document
						.querySelector("input[name='emailDomain']");

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
</script>
<!-- api 우편번호 찾기/자동입력 -->
<script>
	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 선택된 우편번호와 주소 정보를 입력창에 자동 채움
				document.getElementById("zipcode").value = data.zonecode; // 우편번호
				document.getElementById("addr").value = data.roadAddress; // 도로명 주소
			}
		}).open();
	}
</script>

</head>
<body>

	<form action="/join" method="post">
		아이디: <input type="text" name="loginId"  value="${joinformDto.loginId }"/>
		<button type="button">중복확인</button> <br />
		<c:if test="${not empty id_error }"><p>${id_error }</p></c:if>
		
		비밀번호: <input type="password" name="pw" placeholder="8~20자리,영문/숫자/특수문자 포함" /> <br /> 
		비밀번호 확인: <input type="password" name="pw2" /> <br />
		<c:if test="${not empty pw_error }"><p>${pw_error }</p></c:if>
		
		<input type="hidden" name="rolesId" value="100" /> 
		
		이름: <input type="text" name="name" value="${joinformDto.name }"/> <br />
		
		닉네임: <input type="text" name="nickname" value="${joinformDto.nickname }"/>
		<button type="button">중복확인</button> <br /> 
		<c:if test="${not empty nickname_error }"><p>${nickname_error }</p></c:if>
		
		성별: <input type="radio" name="gender" value="M" 
			<c:if test="${joinformDto.gender == 'M' }">checked</c:if>/>남 
			<input type="radio" name="gender" value="F" 
			<c:if test="${joinformDto.gender == 'F' }">checked</c:if>/>여 <br /> 
		
		생년월일: <input type="date" name="birth" min="1900-01-01" max="2099-12-31" value="${joinformDto.birth }"/> <br /> 
		
		연락처: <input type="text" name="tel" placeholder="전화번호 입력"  value="${joinformDto.tel }"/> <br /> 
		
		우편번호: <input type="text" id="zipcode" name="zipcode" readonly value="${joinformDto.zipcode }" />
		<button type="button" onclick="execDaumPostcode()">우편번호 찾기</button> <br /> 
		
		도로명주소:<input type="text" id="addr" name="addr" readonly value="${joinformDto.addr }"  />
		상세주소: <input type="text" name="detailAddr" value="${joinformDto.detailAddr }"/> <br /> 
		
		이메일: <input type="text" name="emailId"  value="${joinformDto.emailId }"/> @ <input type="text" name="emailDomain" value="${joinformDto.emailDomain }" /> 
		<select name="domainSelect">
			<option value="">-- 선택하세요 --</option>
			<option value="naver.com">naver.com</option>
			<option value="gmail.com">gmail.com</option>
			<option value="daum.net">daum.net</option>
			<option value="kakao.com">kakao.com</option>
			<option value="직접입력">직접입력</option>
		<c:if test="${not empty email_error }"><p>${email_error }</p></c:if>
		<input type="submit" value="join" />
	</form>

</body>
</html>