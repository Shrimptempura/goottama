<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h3>권한 등급 변경</h3>
<form id="role-update-form" method="post" action="/admin/users/change_user_role">
    <input type="hidden" name="user_id" value="${userId}">
    <p>
        <strong>현재 등급</strong><br />
        <span>${rolesId}</span>
    </p>
    <p>
    <div class="new-user-role">새 등급을 선택하세요
        <label><input type="radio" name="new_user_role" value="100">100 - 일반회원</label>
        <label><input type="radio" name="new_user_role" value="200">200 - 판매자</label>
        <label><input type="radio" name="new_user_role" value="300">300 - 운영자</label>
    </div>
    </p>
    <button type="submit">변경</button>
</form>