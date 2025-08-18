<%-- withdrawal_reason_page.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Withdrawal Reason</h1>

<%-- 검색바 --%>
<form action="/admin/withdrawal/withdrawal_reason_list" method="post" id="withdrawalReasonSearchForm">
    <div class="search-form">
        <label>탈퇴 아이디 <input type="text" name="withdraw_id" value="${withdrawalReasonSearchDTO.withdraw_id}"></label>
        <label>탈퇴 이유(내용) <input type="text" name="withdrawal_reason" placeholder="탈퇴 이유 내용" value="${withdrawalReasonSearchDTO.withdrawal_reason}"></label>
        <label>탈퇴일 <input type="date" name="withdrawal_date_start" value="${withdrawalReasonSearchDTO.withdrawal_date_start}"></label>
        <label>~ <input type="date" name="withdrawal_date_end" value="${withdrawalReasonSearchDTO.withdrawal_date_end}"></label>
        <div class="withdrawal-reason-group">탈퇴 이유
            <label><input type="checkbox" name="withdrawal_reason_id" value="1">이용빈도낮음</label>
            <label><input type="checkbox" name="withdrawal_reason_id" value="2">상품/정보 부족</label>
            <label><input type="checkbox" name="withdrawal_reason_id" value="3">혜택 부족</label>
            <label><input type="checkbox" name="withdrawal_reason_id" value="4">기타</label>
        </div>
        <input type="hidden" name="page" value="${searchVO.page}">
        <input type="submit" value="검색" />
    </div>
</form>

<%-- 페이징 및 목록 출력 --%>
<div id="withdrawalReasonListContainer">
    <jsp:include page="withdrawal_reason_list.jsp" />
</div>