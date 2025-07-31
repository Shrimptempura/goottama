<!-- user_data_detail.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>User Data Detail</title>
</head>
<body>
    <h1>${userData.login_id}</h1>
    <p>아읻 : ${userData.user_id}</p>
    <p>이름 : ${userData.user_name}</p>
    <p>닉넴 : ${userData.user_nickname}</p>
    <p>성별 : ${userData.user_gender}</p>
    <p>생일 : ${userData.user_birth}</p>
    <p>가입 : ${userData.user_created_at}</p>
    <p>전번 : ${userData.user_tel}</p>
    <p>우편 : ${userData.user_zipcode}</p>
    <p>주소 : ${userData.user_addr}</p>
    <p>이멜 : ${userData.user_email}</p>
    <p>프사 : ${userData.user_img}</p>
    <p>권한 : ${userData.roles_id}</p>
    <p>로긴 : ${userData.login_id}</p>
    <p>제재 : ${userData.sanctions_types}</p>
    <p>상태 : ${userData.user_status}</p>
    <p>정지 : ${userData.user_sanctions_until}</p>

    <c:forEach var="sanction" items="${sanctions}">
    <div><br /><hr /></div>
        <p>제제아읻 : ${sanction.sanctionsId}</p>
        <p>유저아읻 : ${sanction.userId}</p>
        <p>제제종류 : ${sanction.sanctionsTypes}</p>
        <p>제제시작 : ${sanction.sanctionsStartDate}</p>
        <p>제제종료 : ${sanction.sanctionsEndDate}</p>
        <p>제제이유 : ${sanction.sanctionsReason}</p>
        <p>관리자앋 : ${sanction.adminAccountId}</p>
        <p>재제생성 : ${sanction.sanctionsCreatedAt}</p>
    </c:forEach>
</body>
</html>