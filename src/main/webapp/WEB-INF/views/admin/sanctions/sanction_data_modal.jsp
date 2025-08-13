<%-- sanction_data_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="sanction-details-body">
        <h2>${userActivityLog.user_activity_id}번 로그</h2>
        <!-- 유저 정보 -->
        <h3>유저 정보</h3>
        <p>유저 번호 : ${userData.user_id}</p>
        <p>유저 아이디 : ${userData.user_name}</p>
        <button onclick="window.open('/admin/users/user_data_detail?user_id=${userData.user_id}', '_blank')">새 창에서 유저 정보 자세히 보기</button>
        <!-- 로그 정보 -->
         <h3>로그 정보</h3>
        <p>종류 : ${userActivityLog.user_activity_type}</p>
        <p>기록 시간 : ${userActivityLog.user_activity_time}</p>
        <p>타겟 아이디 : ${userActivityLog.user_activity_target_id}</p>
        <p>타겟 타입 : ${userActivityLog.user_activity_target_type}</p>
        <p>상세 내용<br /> ${userActivityLog.user_activity_details}</p>
    </div>
    <!--
    <button onclick="window.open('/admin/logs/log_detail?user_activity_id=${userActivityLog.user_activity_id}', '_blank')">새 창에서 자세히 보기</button>
    -->
</div>

