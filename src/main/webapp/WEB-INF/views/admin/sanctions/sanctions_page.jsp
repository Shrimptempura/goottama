<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>Sanctions</title>
</head>
<body>
    <h1>Sanctions</h1>

        <%-- 검색바 --%>
        <form action="/admin/sanctions/sanctions_page" method="post" id="sanctionSearchForm">
            <div class="search-form">
                <label>회원 ID
                    <input type="number" name="userId" placeholder="회원 ID" value="${sanctionSearchDTO.userId}">
                </label>
                <label>제재 유형
                    <input type="text" name="sanctionsTypes" placeholder="제재 유형" value="${sanctionSearchDTO.sanctionsTypes}">
                </label>
                <label>제재 사유
                    <input type="text" name="sanctionsReason" placeholder="제재 사유" value="${sanctionSearchDTO.sanctionsReason}">
                </label>
                <label>관리자 계정 ID
                    <input type="number" name="adminAccountId" placeholder="관리자 ID" value="${sanctionSearchDTO.adminAccountId}">
                </label>
                <label>검색 시작일
                    <input type="date" name="searchStartDate" value="${sanctionSearchDTO.searchStartDate}">
                </label>
                <label>검색 종료일
                    <input type="date" name="searchEndDate" value="${sanctionSearchDTO.searchEndDate}">
                </label>
                <label>제재 기간 최소(일)
                    <input type="number" name="durationMin" placeholder="최소 기간" value="${sanctionSearchDTO.durationMin}">
                </label>
                <label>제재 기간 최대(일)
                    <input type="number" name="durationMax" placeholder="최대 기간" value="${sanctionSearchDTO.durationMax}">
                </label>
                <input type="hidden" name="page" value="${searchVO.page}">
                <input type="submit" value="검색">
            </div>
        </form>


        <%-- 페이징 및 공지 출력 --%>
        <div id="sanctionListContainer">
            <jsp:include page="sanction_list.jsp" />
        </div>
</body>
</html>