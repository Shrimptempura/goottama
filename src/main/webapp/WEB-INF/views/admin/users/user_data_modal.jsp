<%-- user_detail_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="user-details-body">
        <p>아이디 : ${userData.user_id}</p>
        <p>상태 : ${userData.user_status}</p>
        <p>이름 : ${userData.user_name}</p>
        <p>닉네임 : ${userData.user_nickname}</p>
        <p>성별 : ${userData.user_gender}</p>
        <p>생일 : ${userData.user_birth}</p>
        <p>가입일 : ${userData.user_created_at}</p>
        <p>전화번호 : ${userData.user_tel}</p>
        <p>우편번호 : ${userData.user_zipcode}</p>
        <p>주소 : ${userData.user_addr}</p>
        <p>이메일 : ${userData.user_email}</p>
        <p>프사 : ${userData.user_img}</p>
        <p>등급 : ${userData.roles_id}</p>
        <p>로그인아이디 : ${userData.login_id}</p>
        <p>제재 기간 : ${userData.user_sanctions_until}</p>
    </div>
    <button onclick="window.open('/admin/users/user_data_detail?user_id=${userData.user_id}', '_blank')">새 창에서 자세히 보기</button>
</div>

