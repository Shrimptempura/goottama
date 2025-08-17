<!-- src/main/webapp/WEB-INF/views/common/notice/notice_detail.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="/static/css/admin/public-notice.css">
        <title>${notice.notices_title}</title>
    </head>
    <body>
        <%@ include file="../header_navigation_bar.jsp" %>
        <div class="main-content-container">
            <h1>${notice.notices_title}</h1>
            <div>
                ${notice.notices_created_at}
            </div>
            <div class="contents-container">
            <hr />
                ${notice.notices_content}
                <c:if test="${not empty notice.attachedFiles}">
                    <fieldset>
                    <legend>첨부파일</legend>
                    <c:forEach var="file" items="${notice.attachedFiles}">
                        <a href="/admin/attachments/download?fileId=${file.file_id}">${file.file_name}</a><br />
                    </c:forEach>
                    </fieldset>
                </c:if>
            <hr />
            </div>
            <button type="button" onclick="location.href='./notice_list'">목록 보기</button>
            <a
                href="/admin/reports/reportForm?targetType=NOTICE&targetId=${notice.notices_id}"
                class="report-link"
                onclick="window.open(this.href, '신고하기', 'width=500,height=400'); return false;">
                신고하기
            </a>
        </div>
        <%@ include file="../footer.jsp" %>
    </body>
</html>