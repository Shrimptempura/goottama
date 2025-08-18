<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Report Page</title>
</head>
<body>
        <h1>Report</h1>

        <%-- 검색바 --%>
        <form action="/admin/reports/report_list" method="post" id="reportSearchForm" class="admin-search-form">
            <div class="search-form">
                <span class="form-label">신고 한 유저번호</span>
                <input type="text" name="userId" value="${reportSearchDTO.userId}" class="form-input">

                <span class="form-label">신고내용</span>
                <input type="text" name="reportContent" placeholder="신고내용" value="${reportSearchDTO.reportContent}" class="form-input">

                <span class="form-label">신고일 시작</span>
                <input type="date" name="reportDateStart" value="${reportSearchDTO.reportDateStart}" class="form-input">

                <span class="form-label">신고일 종료</span>
                <input type="date" name="reportDateEnd" value="${reportSearchDTO.reportDateEnd}" class="form-input">

                <span class="form-label">타겟 아이디</span>
                <input type="text" name="targetId" value="${reportSearchDTO.targetId}" class="form-input">

                <span class="form-label">타겟 타입</span>
                <div class="targetType-group form-input">
                    <label><input type="checkbox" name="targetType" value="INTERIOR">INTERIOR</label>
                    <label><input type="checkbox" name="targetType" value="COMMUNITY">COMMUNITY</label>
                    <label><input type="checkbox" name="targetType" value="COMMUNITY_REVIEW">COMMUNITY_REVIEW</label>
                    <label><input type="checkbox" name="targetType" value="REVIEW">REVIEW</label>
                    <label><input type="checkbox" name="targetType" value="COMMENT">COMMENT</label>
                    <label><input type="checkbox" name="targetType" value="SHOP">SHOP</label>
                    <label><input type="checkbox" name="targetType" value="NOTICE">NOTICE</label>
                    <label><input type="checkbox" name="targetType" value="MEMBER">MEMBER</label>
                    <label><input type="checkbox" name="targetType" value="ECT">ECT</label>
                </div>

                <span class="form-label">신고 상태</span>
                <div class="report-status-group form-input">
                    <label><input type="checkbox" name="reportStatus" value="PENDING">PENDING</label>
                    <label><input type="checkbox" name="reportStatus" value="IN_REVIEW">IN_REVIEW</label>
                    <label><input type="checkbox" name="reportStatus" value="RESOLVED">RESOLVED</label>
                    <label><input type="checkbox" name="reportStatus" value="REJECTED">REJECTED</label>
                    <label><input type="checkbox" name="reportStatus" value="CLOSED">CLOSED</label>
                </div>

                <input type="hidden" name="page" value="${searchVO.page}">

                <span class="search-button-container">
                    <input type="submit" value="검색" />
                </span>
            </div>
        </form>

        <%-- 페이징 및 공지 출력 --%>
        <div id="reportListContainer">
            <jsp:include page="report_list.jsp" />
        </div>
</body>
</html>