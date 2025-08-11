<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="/js/member/editPostCode.js"></script>
    <script src="/js/member/previewImg.js"></script>
    <script src="/js/member/checkDuplicateNickname.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 변경</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/editProfile_view.css" />
</head>
<body>

<div class="nav">
<a href="/mypage/myProfile"> 프로필 </a>
<a href="/mypage/myOrderList">나의쇼핑</a>
<a href="/mypage/myReview">나의활동</a>
<a href="/mypage/editProfile_view"><strong>설정</strong></a>
</div>

<div class="sub-nav">
<a href="/mypage/editProfile_view"><strong>회원정보변경</strong></a>
<a href="/mypage/editPassword">비밀번호변경</a>
<a href="/mypage/customerCenter">고객센터</a>
</div>

<div class="profile-container">

 	<h2>회원정보 수정</h2>
  
    <div>
      <p class="info-text">현재 프로필 이미지</p>
      <img class="current-img" src="${pageContext.request.contextPath}${loginMember.profileImgUrl}" alt="프로필 이미지" />
    </div>
    <form action="/profileImgUpload" method="post" enctype="multipart/form-data">
    <label for="profileImg">변경할 프로필 이미지 선택</label>
    <img id="preview" src="#" alt="미리보기 이미지" style="display:none;" />
    <input type="file" accept="image/*" name="profileImg" id="profileImg" onchange="previewImg(event)" />
   
    <input type="submit" class="btn btn-submit" value="프로필 이미지 변경하기" />
  </form>
 
  <form action="/editProfile" method="post">
    <label>아이디</label>
    <div class="info-text">${loginMember.login_id}</div>

    <label for="changeName">이름</label>
    <input type="text" id="changeName" name="changeName" value="${loginMember.user_name}" />

    <label for="nickname">닉네임</label>
    <div class="inline-group">
      <input type="text" id="nickname" name="changeNickname" value="${loginMember.user_nickname}" />
      <button type="button" class="btn btn-secondary" onclick="checkDuplicateNickname()">중복확인</button>
    </div>

    <c:if test="${not empty validationError}">
      <p class="info-text" >${validationError}</p>
    </c:if>

    <label>성별</label>
    <div class="info-text">${loginMember.user_gender}</div>

    <label>생년월일</label>
    <div class="info-text">${loginMember.user_birth}</div>

    <label for="changeTel">연락처</label>
    <input type="text" id="changeTel" name="changeTel" value="${loginMember.user_tel}" />

    <label for="changeZipcode">우편번호</label>
    <div class="inline-group">
      <input type="text" id="changeZipcode" name="changeZipcode" readonly value="${loginMember.user_zipcode}" />
      <button type="button" class="btn btn-secondary" onclick="execDaumPostcode()">우편번호 찾기</button>
    </div>

    <label for="changeAddr">도로명주소</label>
    <input type="text" id="changeAddr" name="changeAddr" readonly value="${loginMember.user_addr}" />

    <label for="changeDetailAddr">상세주소</label>
    <input type="text" id="changeDetailAddr" name="changeDetailAddr" />

    <label>이메일</label>
    <div class="info-text">${loginMember.user_email}</div>

    <label>가입일</label>
    <div class="info-text">${loginMember.user_created_at}</div>

    <input type="submit" class="btn btn-submit" value="변경하기" />
  </form>

  <a href="/mypage/withdrawal_view" class="withdraw-link">탈퇴하기 &gt;</a>
</div>


</body>
</html>