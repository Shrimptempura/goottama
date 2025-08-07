<%@ page import="com.ama.don.admin.service.userManage.ManageUserByAdmin" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %><%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-04
  Time: 오후 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof ManageUserByAdmin) {
        ManageUserByAdmin user = (ManageUserByAdmin) authentication.getPrincipal();
%>
<p>로그인된 유저 ID: <%= user.getUserTotalDataDTO().getLogin_id() %></p>
<p>닉네임: <%= user.getUserNickname() %></p>
<p>유저 번호: <%= user.getUserId() %></p>
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
