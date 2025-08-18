<!-- user_manage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>User Management</title>
</head>
<body>
    <h1>User Management</h1>

    <%-- 검색바 --%>
        <form action="/admin/users/user_list" method="post" id="userSearchForm" >
            <div class="search-form">
                <label>이름 <input type="text" name="user_name" placeholder="이름" value="${userSearchDTO.user_name}"></label>
                <label>user id <input type="text" name="user_id" placeholder="USER ID" value="${userSearchDTO.user_id}"></label>
                <label>권한 <input type="text" name="roles_id" placeholder="권한" value="${userSearchDTO.roles_id}"></label>
                <label>닉네임 <input type="text" name="user_nickname" placeholder="닉네임" value="${userSearchDTO.user_nickname}"></label>
                <div class="user-status-group">
                    <label><input type="checkbox" name="user_status" value="active">Active</label>
                    <label><input type="checkbox" name="user_status" value="suspended">Suspended</label>
                    <label><input type="checkbox" name="user_status" value="deleted">Deleted</label>
                </div>
                <label>가입일 <input type="date" name="create_start_date" value="${userSearchDTO.create_start_date}"></label>
                <label>가입일 <input type="date" name="create_end_date" value="${userSearchDTO.create_end_date}"></label>
                <label>성별 <input type="text" name="user_gender" placeholder="성별" value="${userSearchDTO.user_gender}"></label>
                <label>전화번호 <input type="text" name="user_tel" placeholder="전화번호" value="${userSearchDTO.user_tel}"></label>
                <label>우편번호 <input type="text" name="user_zipcode" placeholder="우편번호" value="${userSearchDTO.user_zipcode}"></label>
                <label>주소 <input type="text" name="user_addr" placeholder="주소" value="${userSearchDTO.user_addr}"></label>
                <label>이메일 <input type="text" name="user_email" placeholder="이메일" value="${userSearchDTO.user_email}"></label>
                <input type="hidden" name="page" value="${searchVO.page}">
                <input type="submit" value="검색" />
            </div>
        </form>

    <!-- 유저 목록 출력 -->
    <div id="userListContainer">
        <jsp:include page="user_list.jsp" />
    </div>
</body>
</html>