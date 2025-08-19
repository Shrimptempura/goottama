<%-- sanction_data_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="sanction-details-body">
        <h2>${list.sanctions_id}번 재제 내용</h2>
        <!-- 유저 정보 -->
        <h3>유저 정보</h3>
        <p>유저 번호 : ${userData.user_id}</p>
        <p>유저 아이디 : ${userData.user_name}</p>
        <button onclick="window.open('/admin/users/user_data_detail?user_id=${userData.user_id}', '_blank')">새 창에서 유저 정보 자세히 보기</button>
        <!-- 재제 정보 -->
         <h3>로그 정보</h3>
        <p>종류 : ${list.sanctions_types}</p>
        <p>기록 시간 : ${list.sanctions_created_at}</p>
        <p>시작일 : ${list.sanctions_start_date}</p>
        <p>종료일 : ${list.sanctions_end_date}</p>
        <p>관리자 아이디 : ${list.admin_account_id}</p>
        <p>재제 기간 : ${list.sanctions_duration}</p>
        <p>상세 내용<br /> ${list.sanctions_reason}</p>
    </div>
    <!--
    <button onclick="window.open('/admin/sanctions/sanction_detail?sanction_id=${list.sanctions_id}', '_blank')">새 창에서 자세히 보기</button>
    -->
</div>

