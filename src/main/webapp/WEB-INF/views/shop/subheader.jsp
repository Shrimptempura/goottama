<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
    .subheader {
        background-color: #f8f9fa;
        border-bottom: 1px solid #ddd;
        padding: 12px 0;
        display: flex;
        justify-content: center;
        gap: 30px;
        font-family: 'Noto Sans KR', sans-serif;
    }

    .subheader a {
        text-decoration: none;
        color: #333;
        font-size: 16px;
        font-weight: 500;
        padding: 8px 12px;
        border-radius: 5px;
        transition: background-color 0.3s, color 0.3s;
    }

    .subheader a:hover {
        background-color: #007bff;
        color: #fff;
    }
</style>
</head>
<body>

<h2>subheader</h2>

<div class="subheader">
<a href="home">home</a>
<a href="category">category</a>
<a href="exhibition">exhibition</a>
<a href="best">best</a>
<a href="todaydeliver">todaydeliver</a>
<a href="cart?user_id=2">cart</a>
</div>

</body>
</html>