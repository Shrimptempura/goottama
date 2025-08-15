<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css" />
    <title>handle member report</title>
  </head>
  <body>
    <h1>handle member report</h1>
    <form action="/admin/reports/change_report_status" method="post">
      <input type="hidden" name="targetType" value="${targetType}">
      <input type="hidden" name="targetId" value="${targetId}">
      <input type="hidden" name="reportId" value="${reportId}">
      report id : ${reportId} <br />
      target type : ${targetType} <br />
      target id : ${targetId} <br />
      <div id="penalty-period">
        <strong>제재 기간 설정</strong>
        <div>
          <label for="startDate">시작 날짜:</label>
          <input type="date" id="startDate" name="startDate" />
        </div>
        <div>
          <label for="endDate">종료 날짜:</label>
          <input type="date" id="endDate" name="endDate" />
        </div>
      </div>
      <label for="status">
        <div id="status-radio-button">
          <strong>신고 처리 상태 변경</strong>
          <div>
            <label>
              <input type="radio" name="status" value="PENDING" /> PENDING
            </label>
          </div>
          <div>
            <label>
              <input type="radio" name="status" value="IN_REVIEW" /> IN_REVIEW
            </label>
          </div>
          <div>
            <label>
              <input type="radio" name="status" value="RESOLVED" /> RESOLVED
            </label>
          </div>
          <div>
            <label>
              <input type="radio" name="status" value="REJECTED" /> REJECTED
            </label>
          </div>
          <div>
            <label>
              <input type="radio" name="status" value="CLOSED" /> CLOSED
            </label>
          </div>
        </div>
      </label>
      <div>
        <label for="sanctionsType">재제 타입</label>
        <input type="text" name="sanctionsType" id="sanctionsType"></input>
       </div>
      <div>
      <div>
        <label for="sanctionReason">재제 사유</label>
        <textarea name="sanctionReason" id="sanctionReason"></textarea>
       </div>
      <div>
        <button type="submit">제출</button>
      </div>
    </form>
  </body>
</html>
