<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
	function execDaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 선택된 우편번호와 주소 정보를 입력창에 자동 채움
				document.getElementById("changeZipcode").value = data.zonecode; // 우편번호
				document.getElementById("changeAddr").value = data.roadAddress; // 도로명 주소
			}
		}).open();
	}
</script>

<script>
	function previewImg(event) {
		const input = event.target;
		const reader = new FileReader();
		
		reader.onload = function(){
			const preview = document.getElementById('preview');
			preview.src = reader.result;
			preview.style.display = 'block';
		};
		if(input.files && input.files[0]){
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>

</head>
<body>

<a href="/mypage/myProfile"> 프로필 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/myOrderList">나의쇼핑</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/myReview">나의활동</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/editProfile_view"><strong>설정</strong></a> <br />

<a href="/mypage/editProfile_view"><strong>회원정보변경</strong></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/editPassword">비밀번호변경</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mypage/customerCenter">고객센터</a> <br />

<form action="/profileImgUpload" method="post" enctype="multipart/form-data">
	<input type="file"  accept="image/*" name="profileImg" onchange="previewImg(event)" />  <br />
	<img id="preview"  src="#" alt="미리보기 이미지" /> <br />
	<img src="${pageContext.request.contextPath }${loginMember.profileImgUrl}" alt="프로필 이미지" /> <br />
	<input type="submit" value="사진변경하기" />
</form>

<form action="/editProfile" method="post">

	아이디:${loginMember.login_id } <br />
	이름:<input type="text" value="${loginMember.user_name }" name="changeName" /> <br />	 
	닉네임:<input type="text" value="${loginMember.user_nickname }" name="changeNickname"/> 
	<button>중복확인</button> <br />	 
	<c:if test="${not empty validationError }"><p>${validationError }</p></c:if>
	성별:${loginMember.user_gender } <br />
	생년월일:${loginMember.user_birth } <br />
	연락처:<input type="text" value="${loginMember.user_tel } " name="changeTel" /> <br />	 
	
	우편번호: <input type="text" id="changeZipcode" name="changeZipcode" readonly value="${loginMember.user_zipcode } " />
	<button type="button" onclick="execDaumPostcode()">우편번호 찾기</button> <br /> 
		
	도로명주소:<input type="text" id="changeAddr" name="changeAddr" readonly value="${loginMember.user_addr }"  />
	상세주소: <input type="text" name="changeDetailAddr" /> <br /> 
 
	이메일:${loginMember.user_email } <br />
	가입일:${loginMember.user_created_at } <br />
	
	<input type="submit" value="변경하기" />
</form>
<a href="/mypage/withdrawal_view">탈퇴하기 ></a>


</body>
</html>