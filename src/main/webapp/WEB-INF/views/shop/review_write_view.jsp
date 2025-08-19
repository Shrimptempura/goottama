<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>리뷰 작성</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f5f5f5;
    margin: 0;
    padding: 0;
}

.review-write-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.page-title {
    text-align: center;
    margin-bottom: 30px;
    font-size: 24px;
    font-weight: bold;
    color: #333;
}

.author-info {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 8px;
    margin-bottom: 20px;
    border-left: 4px solid #007bff;
}

.author-info h3 {
    margin: 0 0 10px 0;
    color: #333;
    font-size: 16px;
}

.author-name {
    font-size: 18px;
    font-weight: bold;
    color: #007bff;
}

.product-card {
    background-color: #fff;
    border: 2px solid #e0e0e0;
    border-radius: 10px;
    padding: 20px;
    margin-bottom: 30px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.product-card h3 {
    margin: 0 0 15px 0;
    color: #333;
    font-size: 18px;
    border-bottom: 2px solid #007bff;
    padding-bottom: 10px;
}

.product-content {
    display: flex;
    gap: 20px;
    align-items: flex-start;
}

.product-image {
    flex-shrink: 0;
    width: 150px;
    height: 150px;
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.product-info {
    flex: 1;
}

.product-name {
    font-size: 20px;
    font-weight: bold;
    color: #333;
    margin-bottom: 10px;
}

.product-mall {
    color: #666;
    font-size: 14px;
    margin-bottom: 8px;
}

.product-price {
    font-size: 18px;
    font-weight: bold;
    color: #ff4444;
    margin-bottom: 5px;
}

.product-discount {
    color: #28a745;
    font-size: 14px;
    font-weight: bold;
}

.review-form {
    background-color: #fff;
}

.form-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

.form-table td {
    padding: 15px;
    border-bottom: 1px solid #eee;
    vertical-align: top;
}

.form-table td:first-child {
    background-color: #f8f9fa;
    font-weight: bold;
    color: #333;
    width: 150px;
    border-right: 1px solid #eee;
}

.form-table select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
}

.form-table input[type="text"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
    box-sizing: border-box;
}

.form-table textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
    resize: vertical;
    min-height: 100px;
    box-sizing: border-box;
}

.form-table input[type="file"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #fff;
}

.submit-section {
    text-align: center;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 2px solid #eee;
}

.submit-btn {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 15px 40px;
    border-radius: 8px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-btn:hover {
    background-color: #0056b3;
}

.cancel-btn {
    background-color: #6c757d;
    color: white;
    border: none;
    padding: 15px 40px;
    border-radius: 8px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    margin-left: 10px;
    transition: background-color 0.3s;
}

.cancel-btn:hover {
    background-color: #545b62;
}

/* 반응형 대응 */
@media (max-width: 768px) {
    .review-write-container {
        margin: 10px;
        padding: 15px;
    }
    
    .product-content {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }
    
    .product-image {
        width: 120px;
        height: 120px;
    }
    
    .form-table td:first-child {
        width: 120px;
    }
}
</style>
</head>

<body>
<div class="review-write-container">
    <h2 class="page-title">리뷰 작성</h2>
    
    <!-- 작성자 정보 -->
    <div class="author-info">
        <h3>작성자</h3>
        <div class="author-name">${user_info.user_nickname}</div>
        <input type="hidden" name="user_id" value="${user_info.user_id}" />
    </div>
    
    <!-- 상품 카드 -->
    <div class="product-card">
        <h3>리뷰 작성 상품</h3>
        <div class="product-content">
            <div class="product-image">
                <c:choose>
                    <c:when test="${not empty product.product_imgurl}">
                        <img src="/static/uploads/shop/${product.product_imgurl}" alt="상품 이미지" />
                    </c:when>
                    <c:otherwise>
                        <img src="/static/uploads/shop/noimages.png" alt="기본 이미지" />
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="product-info">
                <div class="product-name">${product.product_name}</div>
                <div class="product-mall">${product.product_mall_name}</div>
                
                <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
                <c:choose>
                    <c:when test="${hasDiscount}">
                        <c:set var="discountPercent" value="${product.product_discountrate * 100}" />
                        <c:set var="salePrice" value="${product.product_price * (1 - product.product_discountrate)}" />
                        <div class="product-discount"><fmt:formatNumber value="${discountPercent}" pattern="#"/>% 할인</div>
                        <div class="product-price">₩<fmt:formatNumber value="${salePrice}" pattern="#,###"/></div>
                    </c:when>
                    <c:otherwise>
                        <div class="product-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    
    <!-- 리뷰 작성 폼 -->
    <form class="review-form" action="review_write?user_id=${user_info.user_id }&product_id=${product.product_id}" method="post" enctype="multipart/form-data">
        <input type="hidden" name="user_id" value="${user_info.user_id}" />
        <input type="hidden" name="product_id" value="${product.product_id}" />
        
        <table class="form-table">
        
            
            <tr>
                <td>리뷰 제목</td>
                <td>
                    <input type="text" name="review_title" placeholder="리뷰 제목을 입력해주세요" required />
                </td>
            </tr>
            
            <tr>
                <td>리뷰 내용</td>
                <td>
                    <textarea name="review_content" placeholder="상품에 대한 솔직한 후기를 작성해주세요" required></textarea>
                </td>
            </tr>
            			
        </table>
        
        <div class="submit-section">
            <button type="submit" class="submit-btn">리뷰 등록</button>
            <button type="button" class="cancel-btn" onclick="history.back()">취소</button>
        </div>
    </form>
</div>

<script>
// 폼 제출 전 유효성 검사
document.querySelector('.review-form').addEventListener('submit', function(e) {
    const title = document.querySelector('input[name="review_title"]').value.trim();
    const content = document.querySelector('textarea[name="review_content"]').value.trim();
    
    if (!title) {
        alert('리뷰 제목을 입력해주세요.');
        e.preventDefault();
        return;
    }
    
    if (!content) {
        alert('리뷰 내용을 입력해주세요.');
        e.preventDefault();
        return;
    }
    
   
});

// 파일 크기 체크
document.querySelector('input[type="file"]').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
        if (file.size > 5 * 1024 * 1024) { // 5MB
            alert('파일 크기는 5MB 이내로 업로드해주세요.');
            e.target.value = '';
        }
    }
});
</script>
</body>
<%@ include file="../list.jsp" %>
</html>