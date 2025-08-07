<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>Report Page</title>
</head>
<body>
    <div id="toast"></div>

        <h1>Report</h1>

        <%-- 검색바 --%>
        <form action="/admin/reports/report_list" method="post" id="reportSearchForm" >
            <div class="search-form">
                <label>신고 한 유저번호 <input type="text" name="userId" value="${reportSearchDTO.userId}"></label>
                <label>신고내용 <input type="text" name="reportContent" placeholder="신고내용" value="${reportSearchDTO.reportContent}"></label>
                <label>신고일 <input type="date" name="reportDateStart" value="${reportSearchDTO.reportDateStart}"></label>
                <label>신고일 <input type="date" name="reportDateEnd" value="${reportSearchDTO.reportDateEnd}"></label>
                <label>타겟 아이디 <input type="text" name="targetId" value="${reportSearchDTO.targetId}"></label>
                <div class="targetType-group">targetType
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
                <div class="report-status-group">reportStatus
                    <label><input type="checkbox" name="reportStatus" value="PENDING">PENDING</label>
                    <label><input type="checkbox" name="reportStatus" value="IN_REVIEW">IN_REVIEW</label>
                    <label><input type="checkbox" name="reportStatus" value="RESOLVED">RESOLVED</label>
                    <label><input type="checkbox" name="targetType" value="REJECTED">REJECTED</label>
                    <label><input type="checkbox" name="targetType" value="CLOSED">CLOSED</label>
                </div>
                <input type="hidden" name="page" value="${searchVO.page}">
                <input type="submit" value="검색" />
            </div>
        </form>

        <%-- 페이징 및 공지 출력 --%>
        <div id="reportListContainer">
            <jsp:include page="report_list.jsp" />
        </div>
</body>
</html>