<%-- user_manage.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>User Management</title>
</head>
<body>
    <h1>User Management</h1>

    <%-- 검색바 --%>
    <form action="/admin/users/user_list" method="post" id="userSearchForm" class="admin-search-form">
        <div class="search-form">
            <span class="form-label">이름</span>
            <input type="text" name="user_name" placeholder="이름" value="${userSearchDTO.user_name}" class="form-input">

            <span class="form-label">user id</span>
            <input type="text" name="user_id" placeholder="USER ID" value="${userSearchDTO.user_id}" class="form-input">

            <span class="form-label">권한</span>
            <input type="text" name="roles_id" placeholder="권한" value="${userSearchDTO.roles_id}" class="form-input">

            <span class="form-label">닉네임</span>
            <input type="text" name="user_nickname" placeholder="닉네임" value="${userSearchDTO.user_nickname}" class="form-input">

            <span class="form-label">가입일</span>
            <input type="date" name="create_start_date" value="${userSearchDTO.create_start_date}" class="form-input">

            <span class="form-label">가입일</span>
            <input type="date" name="create_end_date" value="${userSearchDTO.create_end_date}" class="form-input">

            <span class="form-label">성별</span>
            <input type="text" name="user_gender" placeholder="성별" value="${userSearchDTO.user_gender}" class="form-input">

            <span class="form-label">전화번호</span>
            <input type="text" name="user_tel" placeholder="전화번호" value="${userSearchDTO.user_tel}" class="form-input">

            <span class="form-label">우편번호</span>
            <input type="text" name="user_zipcode" placeholder="우편번호" value="${userSearchDTO.user_zipcode}" class="form-input">

            <span class="form-label">주소</span>
            <input type="text" name="user_addr" placeholder="주소" value="${userSearchDTO.user_addr}" class="form-input">

            <span class="form-label">이메일</span>
            <input type="text" name="user_email" placeholder="이메일" value="${userSearchDTO.user_email}" class="form-input">

            <span class="form-label">계정 활성 상태</span>
            <div class="user-status-group form-input">
                <label><input type="checkbox" name="user_status" value="active">Active</label>
                <label><input type="checkbox" name="user_status" value="suspended">Suspended</label>
                <label><input type="checkbox" name="user_status" value="deleted">Deleted</label>
            </div>

            <input type="hidden" name="page" value="${searchVO.page}">

            <span class="search-button-container">
                <input type="submit" value="검색" />
            </span>
        </div>
    </form>

    <div id="userListContainer">
        <jsp:include page="user_list.jsp" />
    </div>
</body>
</html>