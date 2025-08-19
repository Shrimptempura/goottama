<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/static/css/admin/simple.min.css">
    <title>관리자 대시보드</title>
    <style>
        body {
            margin: 0;
            padding: 0;
        }
        .admin-dashboard-wrapper {
            display: flex;
            min-height: 100vh;
        }
        .admin-dashboard-container {
            flex-grow: 1;
            padding: 40px;
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .admin-dashboard-info {
            background-color: #B8D0FA;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            color: #333;
        }
        .admin-dashboard-summary-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            color: #212121;
        }
        .admin-dashboard-card {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }
        .admin-dashboard-card h3 {
            margin-top: 0;
            font-size: 1.2em;
            color: #555;
        }
        .admin-dashboard-card p {
            font-size: 2em;
            margin: 5px 0 0;
            font-weight: bold;
        }
        .admin-dashboard-recent-lists {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            color: #212121;
        }
        .admin-dashboard-list-card {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }
        .admin-dashboard-list-card ul {
            list-style: none;
            padding: 0;
        }
        .admin-dashboard-list-card li {
            border-bottom: 1px solid #eee;
            padding: 10px 0;
        }
        .admin-dashboard-list-card li:last-child {
            border-bottom: none;
        }
        .report-item-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
        }
        .report-view-btn {
            font-size: 0.7em;
            padding: 2px 6px;
            margin-left: auto;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
            cursor: pointer;
            border-radius: 4px;
            color: #212121;
        }
    </style>
</head>
<body>
    <div class="admin-dashboard-wrapper">
        <main class="admin-dashboard-container">
            <h1>대시보드</h1>

            <div class="admin-dashboard-info">
                <h2>안녕하세요, ${adminData.user_name} 관리자님!</h2>
                <p><strong>아이디:</strong> ${adminData.login_id}</p>
                <p>
                    <strong>계정 등급:</strong>
                    <c:choose>
                        <c:when test="${adminData.roles_id == 300}">관리자</c:when>
                        <c:when test="${adminData.roles_id == 400}">최고운영자</c:when>
                        <c:otherwise>알 수 없음</c:otherwise>
                    </c:choose>
                </p>
            </div>

            <div class="admin-dashboard-summary-cards">
                <div class="admin-dashboard-card">
                    <h3>오늘 가입한 회원</h3>
                    <p>${newUsersToday}명</p>
                </div>
                <div class="admin-dashboard-card">
                    <h3>이번 주 가입한 회원</h3>
                    <p>${newUsersThisWeek}명</p>
                </div>
                <div class="admin-dashboard-card">
                    <h3>오늘 올라온 새 글</h3>
                    <p>${newPostToday}개</p>
                </div>
                <div class="admin-dashboard-card">
                    <h3>오늘 올라온 새 댓글</h3>
                    <p>${newCommentToday}개</p>
                </div>
                <div class="admin-dashboard-card">
                    <h3>미처리 신고</h3>
                    <p>${inProgressingReports}건</p>
                </div>
            </div>

            <div class="admin-dashboard-recent-lists">
                <div class="admin-dashboard-list-card">
                    <h3>최근 신고 내역</h3>
                    <ul>
                        <c:forEach items="${reportList}" var="report">
                            <li>
                                <div class="report-item-header">
                                    <strong>신고ID:</strong> ${report.reportId} | <strong>신고자:</strong> ${report.userId}
                                    <button type="button" class="open-modal-btn report-view-btn" data-modal-target="/admin/reports/report_data_modal" data-param-name="report_id" data-param-value="${report.reportId}">▶</button>
                                </div>
                                <p>
                                    <small>
                                        ${fn:substring(report.reportContent, 0, 60)}
                                        <c:if test="${fn:length(report.reportContent) > 60}">...</c:if>
                                    </small>
                                </p>
                                <small>신고일: <fmt:formatDate value="${report.reportDate}" pattern="yyyy-MM-dd HH:mm"/></small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="admin-dashboard-list-card">
                    <h3>최근 제재 내역</h3>
                    <ul>
                        <c:forEach items="${sanctionList}" var="sanction">
                            <li>
                                <strong>유저ID:</strong> ${sanction.userId}<br>
                                <small>
                                    <strong>종류:</strong> ${sanction.sanctionsTypes} <br />
                                    <strong>종료일:</strong> <fmt:formatDate value="${sanction.sanctionsEndDate}" pattern="yyyy-MM-dd"/>
                                </small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

        </main>
    </div>
</body>
</html>