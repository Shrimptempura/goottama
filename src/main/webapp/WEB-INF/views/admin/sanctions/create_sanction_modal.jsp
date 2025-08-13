<%-- create_sanction_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="sanction-details-body">
        <h2>${userId}번 유저 재제 부과</h2>
        <form action="/admin/sanctions/create_sanction" method="get" id="createSanctionForm">
            <div class="form-group">
                <label>유저 ID
                    <input type="text" name="user_id" value="${userId}" readonly>
                </label>
            </div>
            <div class="form-group">
                <label>제재 유형
                    <input type="text" name="sanctions_types" placeholder="예: BAN, WARNING">
                </label>
            </div>
            <div class="form-group">
                <label>시작일시
                    <input type="datetime-local" name="sanctions_start_date">
                </label>
            </div>
            <div class="form-group">
                <label>종료일시
                    <input type="datetime-local" name="sanctions_end_date">
                </label>
            </div>
            <div class="form-group">
                <label>제재 사유
                    <textarea name="sanctions_reason" placeholder="사유 입력"></textarea>
                </label>
            </div>
            <div class="form-group">
                <label>관리자 계정
                    <input type="text" name="admin_account" value="${adminId}" readonly>
                </label>
            </div>
            <div class="form-group">
                <input type="submit" value="제재 생성">
            </div>
        </form>
    </div>

</div>

