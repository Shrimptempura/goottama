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

<c:choose>
    <c:when test="${not empty communityList}">
        <div class="community-list">
            <c:forEach var="post" items="${communityList}">
                <div class="community-card">
                    <div class="community-thumbnail">
                        <c:choose>
                          <%--   <c:when test="${not empty post.fileList}">
                                <img src="${post.fileList[0].file_path}" alt="썸네일" />
                            </c:when> --%>
                            <c:otherwise>
                                <img src="/img/no-image.png" alt="이미지 없음" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="community-info" onclick="location.href='${pageContext.request.contextPath}/community/post_detail_view?post_id=${post.post_id}'">
                        <label class="community-title">${post.review_title}</label>
                        <p class="community-content">${post.review_content}</p>
                        <div class="community-meta">
                            <span class="meta-date">
                                <fmt:formatDate value="${post.review_date}" pattern="yyyy.MM.dd" />
                            </span>
                            <span class="meta-count">
 								 조회 <span id="review_count_${post.review_id}">${post.review_count}</span>
							</span>
							<span class="meta-like">
 								 좋아요 <span id="review_like_count_${post.review_id}">${post.review_like_count}</span>
							</span>
                            <span class="meta-comment" id="review_comment_count_${post.review_id}"> 댓글 ${post.comment_count}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="no-post">
            <h3>작성한 게시글이 없습니다.</h3>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>