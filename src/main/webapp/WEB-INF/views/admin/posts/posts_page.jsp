<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Posts Page</title>
</head>
<body>
    <h1>Posts Management</h1>

    <form action="/admin/posts/post_list" method="post" id="postSearchForm" class="admin-search-form">
        <div class="search-form">
            <span class="form-label">게시글 아이디</span>
            <input type="text" name="post_id" placeholder="게시글 아이디" value="${postSearchForAdminDTO.post_id}" class="form-input">

            <span class="form-label">유저 아이디</span>
            <input type="text" name="user_id" placeholder="유저 아이디" value="${postSearchForAdminDTO.user_id}" class="form-input">

            <span class="form-label">게시글 제목</span>
            <input type="text" name="post_title" placeholder="게시글 제목" value="${postSearchForAdminDTO.post_title}" class="form-input">

            <span class="form-label">게시글 내용</span>
            <input type="text" name="post_content" placeholder="게시글 내용" value="${postSearchForAdminDTO.post_content}" class="form-input">

            <span class="form-label">타겟 아이디</span>
            <input type="text" name="targetId" placeholder="타겟 아이디" value="${postSearchForAdminDTO.targetId}" class="form-input">

            <span class="form-label">타겟 타입</span>
            <div class="target-type-group form-input">
                <label><input type="checkbox" name="targetType" value="INTERIOR">INTERIOR</label>
                <label><input type="checkbox" name="targetType" value="INTERIOR_REVIEW">INTERIOR_REVIEW</label>
                <label><input type="checkbox" name="targetType" value="INTERIOR_POST">INTERIOR_POST</label>
                <label><input type="checkbox" name="targetType" value="INTERIOR_PARTIAL">INTERIOR_PARTIAL</label>
                <label><input type="checkbox" name="targetType" value="COMMUNITY">COMMUNITY</label>
                <label><input type="checkbox" name="targetType" value="COMMUNITY_REVIEW">COMMUNITY_REVIEW</label>
                <label><input type="checkbox" name="targetType" value="COMMUNITY_HOUSEPHOTO">COMMUNITY_HOUSEPHOTO</label>
                <label><input type="checkbox" name="targetType" value="COMMUNITY_HOUSEDECORATION">COMMUNITY_HOUSEDECORATION</label>
                <label><input type="checkbox" name="targetType" value="SHOP">SHOP</label>
                <label><input type="checkbox" name="targetType" value="ADMIN">ADMIN</label>
                <label><input type="checkbox" name="targetType" value="MEMBER">MEMBER</label>
            </div>

            <span class="form-label">작성일 시작</span>
            <input type="date" name="post_date_start" value="${postSearchForAdminDTO.post_date_start}" class="form-input">

            <span class="form-label">작성일 종료</span>
            <input type="date" name="post_date_end" value="${postSearchForAdminDTO.post_date_end}" class="form-input">

            <input type="hidden" name="page" value="${searchVO.page}">

            <span class="search-button-container">
                <input type="submit" value="검색" />
            </span>
        </div>
    </form>

    <div id="postListContainer">
        <jsp:include page="post_list.jsp" />
    </div>
</body>
</html>