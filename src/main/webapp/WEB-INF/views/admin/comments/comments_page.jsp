<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>Comments Page</title>
</head>
<body>
    <h1>Comments Management</h1>

    <form action="/admin/comment/comment_list" method="post" id="commentSearchForm">
        <div class="search-form">
            <label>댓글 아이디
                <input type="text" name="commentId" placeholder="댓글 아이디" value="${commentsSearchForAdminDTO.commentId}">
            </label>
            <label>유저 아이디
                <input type="text" name="userId" placeholder="유저 아이디" value="${commentsSearchForAdminDTO.userId}">
            </label>
            <label>댓글 내용
                <input type="text" name="commentContent" placeholder="댓글 내용" value="${commentsSearchForAdminDTO.commentContent}">
            </label>
            <label>타겟 아이디
                <input type="text" name="targetId" placeholder="타겟 아이디" value="${commentsSearchForAdminDTO.targetType}">
            </label>
            <div class="target-type-group">
                <span>타겟 타입</span>
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
            <div class="is-deleted-group">
                <span>삭제 여부</span>
                <label><input type="radio" name="isDeleted" value="0" <c:if test="${commentsSearchForAdminDTO.isDeleted eq 0}"></c:if>>정상</label>
                <label><input type="radio" name="isDeleted" value="1" <c:if test="${commentsSearchForAdminDTO.isDeleted eq 1}"></c:if>>삭제됨</label>
                <label><input type="radio" name="isDeleted" value="" <c:if test="${commentsSearchForAdminDTO.isDeleted eq null}"></c:if>>전체</label>
            </div>
            <label>작성일 시작
                <input type="date" name="createdAtStart" value="${commentsSearchForAdminDTO.createdAtStart}">
            </label>
            <label>작성일 종료
                <input type="date" name="createdAtEnd" value="${commentsSearchForAdminDTO.createdAtEnd}">
            </label>
            <input type="hidden" name="page" value="${searchVO.page}">
            <input type="submit" value="검색">
        </div>
    </form>

    <div id="commentListContainer">
        <jsp:include page="comment_list.jsp" />
    </div>
</body>
</html>