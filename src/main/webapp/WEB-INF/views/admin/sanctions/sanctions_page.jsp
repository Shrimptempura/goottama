<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/static/css/admin/simple.min.css">
<title>Sanctions</title>
</head>
<body>
    <h1>Sanctions</h1>

        <%-- 검색바 --%>
        <form action="/admin/sanctions/sanctions_page" method="post" id="sanctionSearchForm" class="admin-search-form">
            <div class="search-form">
                <span class="form-label">회원 ID</span>
                <input type="number" name="userId" placeholder="회원 ID" value="${sanctionSearchDTO.userId}" class="form-input">

                <span class="form-label">제재 유형</span>
                <div class="form-input">
                    <input type="text" name="sanctionsTypes" placeholder="제재 유형" value="${sanctionSearchDTO.sanctionsTypes}">
                    <div class="input-description">
                      <c:forEach var="type" items="${sanctionTypesList}">
                        ∎${type}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      </c:forEach>
                    </div>
                </div>

                <span class="form-label">제재 사유</span>
                <input type="text" name="sanctionsReason" placeholder="제재 사유" value="${sanctionSearchDTO.sanctionsReason}" class="form-input">

                <span class="form-label">관리자 계정 ID</span>
                <input type="number" name="adminAccountId" placeholder="관리자 ID" value="${sanctionSearchDTO.adminAccountId}" class="form-input">

                <span class="form-label">검색 시작일</span>
                <input type="date" name="searchStartDate" value="${sanctionSearchDTO.searchStartDate}" class="form-input">

                <span class="form-label">검색 종료일</span>
                <input type="date" name="searchEndDate" value="${sanctionSearchDTO.searchEndDate}" class="form-input">

                <span class="form-label">제재 기간 최소(일)</span>
                <input type="number" name="durationMin" placeholder="최소 기간" value="${sanctionSearchDTO.durationMin}" class="form-input">

                <span class="form-label">제재 기간 최대(일)</span>
                <input type="number" name="durationMax" placeholder="최대 기간" value="${sanctionSearchDTO.durationMax}" class="form-input">

                <input type="hidden" name="page" value="${searchVO.page}">

                <span class="search-button-container">
                    <input type="submit" value="검색">
                </span>
            </div>
        </form>


        <%-- 페이징 및 공지 출력 --%>
        <div id="sanctionListContainer">
            <jsp:include page="sanction_list.jsp" />
        </div>
</body>
</html>