<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
    <title>신고하기</title>
</head>
<body>
    <h2>신고하기</h2>
    <form action="/admin/reports/submit_report" method="post">

        <input type="hidden" name="targetType" value="${targetType}">
        <input type="hidden" name="targetId" value="${targetId}">

        <textarea name="reportContent" placeholder="신고 내용을 입력하세요"></textarea>
        <button type="submit">신고 제출</button>
    </form>
</body>
</html>