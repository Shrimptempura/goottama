<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
</head>
<body>
<div class="nav">
<a href="/mypage/myProfile"> <strong>프로필</strong> </a> 
<a href="/mypage/myOrderList">나의쇼핑</a> 
<a href="/mypage/myComment">나의활동</a>
<a href="/mypage/editProfile_view">설정</a> 
</div>

<p class="section-title">현재 프로필 이미지</p>
  	<img class="current-img" src="${pageContext.request.contextPath}${loginMember.profileImgUrl}" alt="프로필 이미지" />
  	<div class="info-text">${loginMember.login_id}</div>
  	<div class="info-text">${loginMember.user_created_at}</div>
<c:forEach var="review" items="${communityList}">
    <label class="community-title">${review.review_title}</label>
    <p class="community-content">${review.review_content}</p>
</c:forEach>
</body>
</html>