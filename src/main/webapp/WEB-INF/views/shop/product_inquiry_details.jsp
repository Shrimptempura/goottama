<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>베스트 상품</title>
  
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
    }
    .inquiry-container {
        max-width: 900px;
        margin: 30px auto;
        display: flex;
        flex-direction: column;
        gap: 20px;
    }
    .inquiry-card {
        display: flex;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        overflow: hidden;
        padding: 15px;
        gap: 15px;
    }
    .inquiry-card img {
        width: 120px;
        height: 120px;
        object-fit: cover;
        border-radius: 8px;
    }
    .inquiry-content {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .inquiry-title {
        font-size: 1.2rem;
        font-weight: bold;
        margin-bottom: 8px;
    }
    .inquiry-text {
        font-size: 1rem;
        margin-bottom: 10px;
    }
    .reply-box {
        background: #f1f3f5;
        border-radius: 8px;
        padding: 8px 10px;
        font-size: 0.9rem;
        color: #495057;
        margin-top: 8px;
    }
</style>

</head>
<body>
	
	

   <h2>product_inquiry_detail</h2>
	
	<div class="inquiry-container">
	    <c:forEach var="inquiry" items="${inquiry}">
	        <div class="inquiry-card">
	            <img src="/static/uploads/shop/${inquiry.product_imgurl}" alt="상품 이미지" />
	
	            <div class="inquiry-content">
	                <div>
	                    <div class="inquiry-title">
	                        문의 ID: ${inquiry.pinquiry_id}
	                    </div>
	                    <div class="inquiry-text">
	                        문의 내용: ${inquiry.pinquiry_content}
	                    </div>
	                    <div style="font-size:0.85rem; color:#6c757d;">
	                        날짜: ${inquiry.pinquiry_date}  
	                        | 상품 ID: ${inquiry.product_id}  
	                        | 상품명: ${inquiry.product_name}
	                    </div>
	                </div>
	
	                <c:if test="${not empty inquiry.preply_content}">
	                    <div class="reply-box">
	                        <strong>답글:</strong> ${inquiry.preply_content}
	                    </div>
	                </c:if>
	            </div>
	        </div>
	    </c:forEach>
	</div>
	
	
	
</html>