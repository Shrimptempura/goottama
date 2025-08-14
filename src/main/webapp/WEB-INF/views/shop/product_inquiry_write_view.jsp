<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>상품 문의 작성</title>
<style>
    .inquiry-container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background: #fff;
    }
    
    .page-title {
        font-size: 2rem;
        font-weight: bold;
        color: #333;
        text-align: center;
        margin-bottom: 30px;
        padding-bottom: 15px;
        border-bottom: 2px solid #007bff;
    }
    
    .product-info-section {
        background: #f8f9fa;
        border-radius: 10px;
        padding: 20px;
        margin-bottom: 30px;
        border: 1px solid #e9ecef;
    }
    
    .product-display {
        display: flex;
        gap: 20px;
        align-items: center;
    }
    
    .product-image {
        width: 120px;
        height: 120px;
        border-radius: 8px;
        overflow: hidden;
        flex-shrink: 0;
        background: #fff;
        border: 1px solid #dee2e6;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    
    .product-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    
    .no-image {
        color: #6c757d;
        font-size: 0.9rem;
        text-align: center;
    }
    
    .product-details {
        flex: 1;
    }
    
    .product-name {
        font-size: 1.3rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 10px;
    }
    
    .product-price-info {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 5px;
    }
    
    .discount-rate {
        background: #e74c3c;
        color: white;
        padding: 2px 8px;
        border-radius: 4px;
        font-size: 0.9rem;
        font-weight: bold;
    }
    
    .current-price {
        font-size: 1.2rem;
        font-weight: bold;
        color: #e74c3c;
    }
    
    .original-price {
        font-size: 0.9rem;
        color: #6c757d;
        text-decoration: line-through;
    }
    
    .inquiry-form {
        background: white;
        border: 1px solid #e9ecef;
        border-radius: 10px;
        padding: 25px;
    }
    
    .form-group {
        margin-bottom: 20px;
    }
    
    .form-label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #333;
        font-size: 1rem;
    }
    
    .required {
        color: #e74c3c;
    }
    
    .form-control {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #ced4da;
        border-radius: 6px;
        font-size: 1rem;
        transition: border-color 0.3s, box-shadow 0.3s;
        box-sizing: border-box;
    }
    
    .form-control:focus {
        outline: none;
        border-color: #007bff;
        box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.1);
    }
    
    .form-select {
        background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='%23343a40' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='m1 6 7 7 7-7'/%3e%3c/svg%3e");
        background-repeat: no-repeat;
        background-position: right 12px center;
        background-size: 16px 12px;
        appearance: none;
        padding-right: 40px;
    }
    
    .form-textarea {
        min-height: 150px;
        resize: vertical;
        font-family: inherit;
    }
    
    .char-count {
        text-align: right;
        color: #6c757d;
        font-size: 0.9rem;
        margin-top: 5px;
    }
    
    .btn-group {
        display: flex;
        gap: 10px;
        justify-content: center;
        margin-top: 30px;
    }
    
    .btn {
        padding: 12px 30px;
        border: none;
        border-radius: 6px;
        font-size: 1rem;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.3s;
        text-decoration: none;
        display: inline-block;
        text-align: center;
        min-width: 120px;
    }
    
    .btn-primary {
        background: #007bff;
        color: white;
    }
    
    .btn-primary:hover {
        background: #0056b3;
        transform: translateY(-1px);
    }
    
    .btn-secondary {
        background: #6c757d;
        color: white;
    }
    
    .btn-secondary:hover {
        background: #545b62;
        color: white;
        text-decoration: none;
    }
    
    .debug-info {
        background: #f8f9fa;
        border: 1px solid #dee2e6;
        border-radius: 6px;
        padding: 10px;
        margin-bottom: 20px;
        font-size: 0.9rem;
        color: #495057;
    }
    
    .user-info-display {
        margin: 0;
        padding: 12px 15px;
        background: #f8f9fa;
        border: 1px solid #e9ecef;
        border-radius: 6px;
        color: #495057;
        font-size: 1rem;
        min-height: 48px;
        display: flex;
        align-items: center;
    }
    
    .user-info-display .user-icon {
        margin-right: 8px;
        color: #007bff;
    }
</style>
</head>
<body>
<div class="inquiry-container">
    <h2 class="page-title">📝 상품 문의 작성</h2>
    
    <!-- 상품 정보 표시 -->
    <div class="product-info-section">
        <h3 style="margin-bottom: 15px; color: #495057;">문의 상품</h3>
        <div class="product-display">
            <div class="product-image">
                <c:choose>
                    <c:when test="${not empty product.product_imgurl}">
                        <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                    </c:when>
                    <c:otherwise>
                        <div class="no-image">이미지<br>없음</div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="product-details">
                <div class="product-name">${product.product_name}</div>
                <div class="product-price-info">
                    <c:if test="${product.product_discountrate > 0}">
                        <span class="discount-rate">${product.product_discountrate}%</span>
                        <span class="original-price">
                            <fmt:formatNumber value="${product.product_price / (1 - product.product_discountrate/100)}" 
                                            pattern="#,###"/>원
                        </span>
                    </c:if>
                </div>
                <div class="current-price">
                	<!-- 상품가격을 바꾸는 것 -->
                    <fmt:formatNumber value="${product.product_price * (1-product.product_discountrate)}" pattern="#,###"/>원
                    
                </div>
            </div>
        </div>
    </div>
   <%--  
    <!-- 디버깅 정보 (개발용) - 사용자 정보 포함 -->
    <div class="debug-info">
        <strong>🔍 디버깅 정보:</strong><br>
        유저 아이디: <span id="debug_session_raw">${loginMember.user_id}</span><br>
        사용자 닉네임: <span style="color: #007bff;">${loginMember.user_nickname} </span><br>
        사용자 이메일: <span style="color: #007bff;">${loginMember.user_email} </span><br>
        사용자 연락처: <span style="color: #007bff;">${loginMember.user_tel} </span><br>
        폼 제출 상태: <span id="debug_status">대기중</span><br>
        <button type="button" onclick="testUserId()" style="margin-top: 5px; padding: 5px 10px; background: #17a2b8; color: white; border: none; border-radius: 3px;">user_id 테스트</button>
    </div> --%>
    
    <!-- 문의 작성 폼 - 방법 1 적용 -->
    <form class="inquiry-form" 
          action="product_inquiry_write" 
          method="post" 
          onsubmit="return setUserId()">
        
        <!-- 숨겨진 필드들 -->
        <input type="hidden" name="product_id" value="${product.product_id}">
        <input type="hidden" name="user_id" id="user_id_field" value="${loginMember.user_id }">     
        
        <!-- 문의 유형 -->
        <div class="form-group">
            <label for="inquiry_type" class="form-label">문의 유형 <span class="required">*</span></label>
            <select id="inquiry_type" name="inquiry_type" class="form-control form-select" required>
                <option value="">문의 유형을 선택해주세요</option>
                <option value="PRODUCT">상품 정보</option>
                <option value="DELIVERY">배송 문의</option>
                <option value="SIZE">사이즈/색상</option>
                <option value="STOCK">재입고 문의</option>
                <option value="DEFECT">불량/하자</option>
                <option value="RETURN">교환/반품</option>
                <option value="ETC">기타</option>
            </select>
        </div>
      
        <!-- 이름은 일반필드 -->
        <div class="form-group">
            <label for="inquirer_name" class="form-label">이름 <span class="required">*</span></label>
            <p class="user-info-display">
                <span class="user-icon">👤</span>
                ${loginMember.user_nickname }
            </p>
            <input type="hidden" name="inquirer_name" value="${loginMember.user_nickname }">
        </div>
        
        
        <!-- 문의 내용 -->
        <div class="form-group">
            <label for="inquiry_content" class="form-label">문의 내용 <span class="required">*</span></label>
            <textarea id="inquiry_content" name="pinquiry_content" class="form-control form-textarea" 
                      placeholder="문의하실 내용을 자세히 작성해주세요.

• 상품의 사이즈, 색상, 재질 등 궁금한 점
• 배송 관련 문의사항  
• 교환/반품 관련 문의
• 기타 궁금한 사항

상세하게 작성해주시면 더 정확한 답변을 드릴 수 있습니다." 
                      maxlength="1000" required oninput="updateCharCount(this)"></textarea>
            <div class="char-count">
                <span id="current-count">0</span> / 1000자
            </div>
        </div>
        
        <!-- 공개여부 -->
        <!-- <div class="form-group">
            <label for="is_public" class="form-label">공개 설정</label>
            <select id="is_public" name="is_public" class="form-control form-select">
                <option value="0">비공개 (나만 볼 수 있음)</option>
                <option value="1">공개 (다른 고객도 볼 수 있음)</option>
            </select>
        </div> -->
        
        <!-- 버튼 그룹 -->
        <div class="btn-group">
            <button type="submit" class="btn btn-primary">💌 문의 등록</button>
            <a href="product_detail?product_id=${product.product_id}" class="btn btn-secondary">🔙 상품으로 돌아가기</a>
        </div>
    </form>
</div>

<script>

// 글자 수 카운터
function updateCharCount(textarea) {
    const currentLength = textarea.value.length;
    const maxLength = textarea.maxLength;
    document.getElementById('current-count').textContent = currentLength;
    
    // 글자 수가 90% 이상이면 색상 변경
    const charCountElement = document.querySelector('.char-count');
    if (currentLength >= maxLength * 0.9) {
        charCountElement.style.color = '#e74c3c';
    } else {
        charCountElement.style.color = '#6c757d';
    }
}

// 연락처 자동 하이픈 추가
document.getElementById('inquirer_phone').addEventListener('input', function(e) {
    let value = e.target.value.replace(/[^0-9]/g, '');
    if (value.length >= 3 && value.length <= 7) {
        value = value.replace(/(\d{3})(\d+)/, '$1-$2');
    } else if (value.length > 7) {
        value = value.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
    }
    e.target.value = value;
});

// 페이지 로드 시 디버깅 정보 초기화 (강화된 버전)
document.addEventListener('DOMContentLoaded', function() {
    var rawUserId = '${sessionScope.user_id}';
    var processedUserId = getUserId();
    
    document.getElementById('debug_session_raw').textContent = rawUserId || 'null/undefined';
    document.getElementById('debug_session_type').textContent = typeof rawUserId;
    document.getElementById('debug_user_id').textContent = processedUserId;
    
    console.log('페이지 로드 완료');
    console.log('세션 원본 user_id:', rawUserId);
    console.log('처리된 user_id:', processedUserId);
});

</script>
</body>
</html>