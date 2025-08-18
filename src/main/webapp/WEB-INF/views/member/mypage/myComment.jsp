<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="/js/community/update_review_count.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/myComment.css" />
</head>
<body>

<div class="nav">
<a href="/mypage/myProfile"> 프로필</a> 
<a href="/mypage/myOrderList">나의쇼핑</a> 
<a href="/mypage/myComment"> <strong>나의활동</strong> </a> 
<a href="/mypage/editProfile_view">설정</a> 
</div>

<div class="sub-nav">
<a href="/mypage/myComment"><strong>나의 게시글</strong></a>
<a href="/mypage/myReview">내가남긴리뷰</a> 
</div>

<div class="post-container">
    <c:forEach var="post" items="${mapList}">
        <div class="post-card" onclick="location.href='${pageContext.request.contextPath}/community/post_detail_view?post_id=${post.post_id}'">
            <div class="post-title">${post.post_title}</div>
            <div class="post-content">${post.post_content}</div>
            <div class="post-date">${post.post_date}</div>
        </div>
    </c:forEach>
</div>


</body>
</html>