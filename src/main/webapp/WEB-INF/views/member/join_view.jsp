<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function checkId(){
	const loginId = document.getElementById("loginId").value;
	if (!loginId) {
        document.getElementById("result").innerText = "아이디를 입력하세요.";
        return;
    }
	fetch('/checkId?loginId=' + encodeURIComponent(loginId))
	 .then(response => response.json())
                .then(data => {
                    if (data.exists) {
                        document.getElementById("result").innerText = "이미 사용 중인 아이디입니다.";
                    } else {
                        document.getElementById("result").innerText = "사용 가능한 아이디입니다.";
                    }
                })
}
</script>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const domainSelect = document.querySelector("select[name='domainSelect']");
    const domainInput = document.querySelector("input[name='emailDomain']");

    domainSelect.addEventListener("change", function () {
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
<script>
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 선택된 우편번호와 주소 정보를 입력창에 자동 채움
            document.getElementById("zipcode").value = data.zonecode; // 우편번호
            document.getElementById("addr").value = data.roadAddress; // 도로명 주소
        }
    }).open();
}
</script>

</head>
<body>

	<form action="join" method="post">
		아이디: <input type="text" name="loginId" id="loginId" placeholder="아이디를 입력하세요." />
		<button type="button" onclick="checkId()">중복확인</button>
		<p id="result"></p>
		비밀번호: <input type="password" name="pw" /> <br /> 
		비밀번호 확인: <input type="password" name="pw2" /> <br />
		권한: <input type="radio" name="roles" value="1" checked/>일반회원
		<input type="radio" name="roles" value="2" /> 업체 <br /> 
		이름: <input type="text" name="name" /> <br /> 
		닉네임: <input type="text" name="nickname" /> <br /> 
		성별: <input type="radio" name="gender" value="M" checked />남 
			<input type="radio" name="gender" value="F" />여 <br />
		생년월일: <input type="date" name="birth" /> <br /> 
		연락처: <input type="text" name="tel" id="phone" placeholder="전화번호 입력"/> <br /> 
		우편번호: <input type="text" id="zipcode" name="zipcode" readonly/>
		<button type="button" onclick="execDaumPostcode()">우편번호 찾기</button> <br /> 
		도로명주소:<input type="text" id="addr" name="addr" readonly />
		상세주소: <input type="text"  name="addr2" /> <br />
		이메일: <input type="text" name="emailId" /> @
		<input type="text" name="emailDomain"  />
		<select name="domainSelect" >
  		  <option value="">-- 선택하세요 --</option>
   		  <option value="naver.com">naver.com</option>
  		  <option value="gmail.com">gmail.com</option>
  		  <option value="daum.net">daum.net</option>
  		  <option value="kakao.com">kakao.com</option>
  		  <option value="직접입력">직접입력</option>
		</select> <br />
		<input type="submit" value="join" />
	</form>

</body>
</html>