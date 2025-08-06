<%-- user_detail_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="report-details-body">
        <h1>${report.targetType} 신고</h1>
        신고 번호 : ${report.reportId} </br>
        처리 상태 : ${report.reportStatus} </br>
        신고자 : <a href="/admin/users/user_data_detail?user_id=${reporter.user_id}" target="_blank">${reporter.user_name}</a><br/>
        신고일 : ${report.reportDate} </br>
        신고 내용 : ${report.reportContent} </br>

        <div class="reported-data-container">
            <h2>신고 대상 정보</h2>
            <hr />
            <c:if test="${reported.type == 'NOTICE'}">
                <%@ include file="reported_page/noticeReportedDataPage.jsp" %>
            </c:if>
            <c:if test="${reported.type == 'MEMBER'}">
                <%@ include file="reported_page/memberReportedDataPage.jsp" %>
            </c:if>
            <c:if test="${reported.type == 'POST'}">
                <%@ include file="reported_page/postReportedDataPage.jsp" %>
            </c:if>
            <c:if test="${reported.type == 'COMMENT'}">
                <%@ include file="reported_page/commentReportedDataPage.jsp" %>
            </c:if>
        </div>
        <a href="/admin/reports/delete_report">신고 내역 삭제하기</a>
    </div>
</div>

