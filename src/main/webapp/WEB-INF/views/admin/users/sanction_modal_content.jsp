<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3>제재 기간 변경</h3>
<form id="sanction-update-form" method="post" action="/admin/users/change_user_sanctions_until">
    <input type="hidden" name="user_id" value="${userId}">
    <p>
        <label for="new-sanctions-until">새로운 제재 종료일:</label>
        <input type="date" id="new-sanctions-until" name="user_sanctions_until" required />
    </p>
    <p>
    <div class="new-user-status">사용자 상태
        <label><input type="radio" name="new_user_status" value="active">Active</label>
        <label><input type="radio" name="new_user_status" value="suspended">Suspended</label>
        <label><input type="radio" name="new_user_status" value="deleted">Deleted</label>
    </div>
    </p>
    <button type="submit">변경</button>
</form>