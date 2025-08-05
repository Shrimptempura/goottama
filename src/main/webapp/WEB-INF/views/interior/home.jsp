<%@ page import="com.ama.don.member.dto.MemberDto" %><%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-04
  Time: 오후 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%
    MemberDto member = (MemberDto) session.getAttribute("loginMember");
    if (member != null) {
%>
<p>로그인된 유저 ID: <%= member.getLogin_id() %></p>
<p>닉네임: <%= member.getUser_nickname() %></p>
<p>유저 번호: <%= member.getUser_id() %></p>
<%
} else {
%>
<p>로그인되지 않음</p>
<%
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>interior home</h3>

</body>
</html>
