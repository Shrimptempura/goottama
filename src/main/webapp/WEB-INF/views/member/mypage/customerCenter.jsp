<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/js/member/openKakao.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아마겟돈 고객센터</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/customerCenter.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
</head>
<body>
  <div class="nav">
    <a href="/mypage/myProfile">프로필</a>
    <a href="/mypage/myOrderList">나의쇼핑</a>
    <a href="/mypage/myReview">나의활동</a>
    <a href="/mypage/editProfile_view"><strong>설정</strong></a>
  </div>

  <div class="sub-nav">
    <a href="/mypage/editProfile_view">회원정보변경</a>
    <a href="/mypage/editPassword">비밀번호변경</a>
    <a href="/mypage/customerCenter"><strong>고객센터</strong></a>
  </div>

  <div class="customer-center">
    <h3>고객센터</h3>
    <div class="info">
      <span>상담 가능 시간: <strong>09:00 ~ 18:00</strong></span>
      <span>(주말, 공휴일 제외)</span>
      <span>☎️ Tel: <strong>1234-5678</strong></span>
    </div>

    <button type="button" onclick="sendKakaoInquiry()" class="btn">1:1 카톡 상담하기</button>

    <div class="customer-links">
      <a href="/inquiryEmail" class="btn">이메일 문의하기</a>
    </div>
  </div>

</body>
</html>