<!-- user_manage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/static/css/admin/ui-snippets/modal.css">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>User Management</title>
</head>
<body>
    <h1>User Management</h1>

    <%-- 검색바 --%>
        <form action="/admin/users/user_list" method="post" id="userSearchForm" >
            <div class="search-form">
                <label>이름 <input type="text" name="user_name" placeholder="이름" value="${userSearchVO.user_name}"></label>
                <label>닉네임 <input type="text" name="user_nickname" placeholder="닉네임" value="${userSearchVO.user_nickname}"></label>
                <label>가입일 <input type="date" name="create_start_date" value="${userSearchVO.create_start_date}"></label>
                <label>가입일 <input type="date" name="create_end_date" value="${userSearchVO.create_end_date}"></label>
                <label>성별 <input type="text" name="user_gender" placeholder="닉네임" value="${userSearchVO.user_gender}"></label>
                <label>전화번호 <input type="text" name="user_tel" placeholder="닉네임" value="${userSearchVO.user_tel}"></label>
                <label>우편번호 <input type="text" name="user_zipcode" placeholder="닉네임" value="${userSearchVO.user_zipcode}"></label>
                <label>주소 <input type="text" name="user_addr" placeholder="닉네임" value="${userSearchVO.user_addr}"></label>
                <label>이메일 <input type="text" name="user_email" placeholder="닉네임" value="${userSearchVO.user_email}"></label>
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