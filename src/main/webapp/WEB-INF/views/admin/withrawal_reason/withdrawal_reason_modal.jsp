<%-- withdrawal_reason_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="withdrawal-reason-details-body">
        <div class="detail-row">
            <span class="detail-label">탈퇴 아이디:</span>
            <span class="detail-value">${withdrawalReasonDTO.withdraw_id}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">탈퇴 날짜:</span>
            <span class="detail-value">
                <fmt:formatDate value="${withdrawalReasonDTO.withdrawal_date}" pattern="yyyy년 MM월 dd일 HH:mm:ss"/>
            </span>
        </div>
        <div class="detail-row">
            <span class="detail-label">탈퇴 사유 ID:</span>
            <span class="detail-value">${withdrawalReasonDTO.withdrawal_reason_id}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">탈퇴 사유:</span>
            <span class="detail-value">${withdrawalReasonDTO.withdrawal_reason}</span>
        </div>
    </div>
</div>