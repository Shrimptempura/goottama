<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 발생</title>
    <style>
        body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f8f9fa; }
        .container { text-align: center; padding: 40px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
        .heading { font-size: 2.5rem; color: #dc3545; margin-bottom: 1rem; }
        .text { font-size: 1.2rem; color: #6c757d; margin-bottom: 2rem; }
        .button { display: inline-block; padding: 10px 20px; font-size: 1rem; color: #ffffff; background-color: #dc3545; border: none; border-radius: 5px; text-decoration: none; transition: background-color 0.3s ease; }
        .button:hover { background-color: #c82333; }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="heading">오류가 발생했습니다</h1>
        <p class="text">죄송합니다. 요청을 처리하는 중 문제가 발생했습니다.</p>
        <a href="/" class="button">메인 페이지로 돌아가기</a>
    </div>
</body>
</html>