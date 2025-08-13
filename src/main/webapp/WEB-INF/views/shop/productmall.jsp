<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>상품몰 - ProductMall</title>

<style>
/* 전체 레이아웃 */
body {
    font-family: 'Noto Sans KR', sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f8f9fa;
    line-height: 1.6;
}

.container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 20px;
    display: flex;
    gap: 30px;
}

/* 왼쪽 사이드바 - 쇼핑몰 카테고리 */
.sidebar {
    width: 250px;
    background: white;
    border-radius: 15px;
    padding: 25px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    height: fit-content;
    position: sticky;
    top: 20px;
}

.sidebar h3 {
    font-size: 18px;
    font-weight: 700;
    color: #333;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #007bff;
}

.mall-category {
    margin-bottom: 8px;
}

.mall-category a {
    display: block;
    padding: 12px 15px;
    text-decoration: none;
    color: #666;
    font-weight: 500;
    border-radius: 8px;
    transition: all 0.3s ease;
    position: relative;
}

.mall-category a:hover {
    background-color: #e3f2fd;
    color: #007bff;
    padding-left: 20px;
}

.mall-category a.active {
    background-color: #007bff;
    color: white;
    font-weight: 600;
}

.mall-category .count {
    float: right;
    background-color: #f8f9fa;
    color: #666;
    padding: 2px 8px;
    border-radius: 12px;
    font-size: 12px;
}

.mall-category a.active .count {
    background-color: rgba(255, 255, 255, 0.2);
    color: white;
}

/* 메인 컨텐츠 */
.main-content {
    flex: 1;
}

/* 페이지 헤더 */
.page-header {
    text-align: center;
    margin-bottom: 30px;
    padding: 30px 0;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-radius: 15px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.page-header h1 {
    font-size: 2.5rem;
    margin: 0;
    font-weight: 700;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.page-header p {
    font-size: 1.1rem;
    margin: 10px 0 0 0;
    opacity: 0.9;
}

/* 선택된 카테고리 표시 */
.selected-category {
    background: white;
    padding: 20px;
    border-radius: 10px;
    margin-bottom: 25px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    border-left: 4px solid #007bff;
}

.selected-category h2 {
    margin: 0 0 5px 0;
    color: #333;
    font-size: 20px;
}

.selected-category p {
    margin: 0;
    color: #666;
    font-size: 14px;
}

/* 정렬 바 */
.sort-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 25px;
    padding: 15px 20px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.sort-left {
    font-weight: 500;
    color: #333;
}

.sort-right {
    display: flex;
    gap: 10px;
    align-items: center;
}

.sort-select {
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
    background: white;
}

/* 상품 그리드 */
.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 25px;
    margin-bottom: 40px;
}

/* 상품 카드 */
.product-card {
    background: white;
    border-radius: 15px;
    overflow: hidden;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    cursor: pointer;
    position: relative;
}

.product-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}

/* 상품 이미지 */
.product-image {
    position: relative;
    width: 100%;
    height: 220px;
    background: linear-gradient(45deg, #f0f2f5, #e9ecef);
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.product-card:hover .product-image img {
    transform: scale(1.05);
}

.product-image .no-image {
    color: #6c757d;
    font-size: 48px;
    opacity: 0.5;
}

/* 할인 뱃지 */
.discount-badge {
    position: absolute;
    top: 15px;
    left: 15px;
    background: linear-gradient(45deg, #ff6b6b, #ee5a52);
    color: white;
    padding: 5px 10px;
    border-radius: 15px;
    font-size: 12px;
    font-weight: bold;
    box-shadow: 0 2px 8px rgba(255, 107, 107, 0.3);
}

/* NEW 뱃지 */
.new-badge {
    position: absolute;
    top: 15px;
    right: 15px;
    background: linear-gradient(45deg, #4ecdc4, #44a08d);
    color: white;
    padding: 5px 10px;
    border-radius: 15px;
    font-size: 12px;
    font-weight: bold;
    box-shadow: 0 2px 8px rgba(78, 205, 196, 0.3);
}

/* 상품 정보 */
.product-info {
    padding: 20px;
}

.product-mall {
    font-size: 12px;
    color: #007bff;
    margin-bottom: 8px;
    font-weight: 600;
    text-transform: uppercase;
}

.product-name {
    font-size: 16px;
    font-weight: 600;
    color: #333;
    margin-bottom: 12px;
    line-height: 1.4;
    height: 44px;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}

/* 가격 정보 */
.product-price {
    display: flex;
    flex-direction: column;
    gap: 5px;
}

.original-price {
    font-size: 14px;
    color: #999;
    text-decoration: line-through;
}

.current-price {
    font-size: 18px;
    font-weight: bold;
    color: #ff6b6b;
}

.discount-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 5px;
}

.discount-rate {
    background: linear-gradient(45deg, #ff6b6b, #ee5a52);
    color: white;
    padding: 2px 8px;
    border-radius: 10px;
    font-size: 12px;
    font-weight: bold;
}

/* 액션 버튼들 */
.product-actions {
    display: flex;
    gap: 8px;
    margin-top: 15px;
}

.action-btn {
    flex: 1;
    padding: 8px 12px;
    border: none;
    border-radius: 8px;
    font-size: 12px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
}

.cart-btn {
    background-color: #007bff;
    color: white;
}

.cart-btn:hover {
    background-color: #0056b3;
    transform: translateY(-1px);
}

.order-btn {
    background-color: #28a745;
    color: white;
}

.order-btn:hover {
    background-color: #1e7e34;
    transform: translateY(-1px);
}

/* 빈 상태 */
.empty-state {
    text-align: center;
    padding: 80px 20px;
    color: #6c757d;
    grid-column: 1 / -1;
}

.empty-state i {
    font-size: 64px;
    margin-bottom: 20px;
    opacity: 0.5;
}

.empty-state h3 {
    font-size: 24px;
    margin-bottom: 10px;
    color: #495057;
}

.empty-state p {
    font-size: 16px;
    margin-bottom: 30px;
}

.empty-state .btn {
    display: inline-block;
    padding: 12px 24px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 25px;
    font-weight: 500;
    transition: all 0.3s ease;
}

.empty-state .btn:hover {
    background-color: #0056b3;
    transform: translateY(-2px);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
    .container {
        flex-direction: column;
        padding: 15px;
        gap: 20px;
    }
    
    .sidebar {
        width: 100%;
        position: static;
    }
    
    .page-header h1 {
        font-size: 2rem;
    }
    
    .product-grid {
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
    }
}

@media (max-width: 480px) {
    .product-grid {
        grid-template-columns: 1fr;
    }
}
</style>
</head>
<body>

<div class="container">
    <!-- 왼쪽 사이드바 - 쇼핑몰 카테고리 -->
	<div class="sidebar">
	    <h3>🏪 쇼핑몰별 보기</h3>
	    
	    <!-- 전체 보기 -->
	    <div class="mall-category">
	        <a href="/shop/productmall" class="${empty param.mall_name ? 'active' : ''}">
	            전체 쇼핑몰
	        </a>
	    </div>
	    
	    <!-- 🔧 수정: 중복 제거된 쇼핑몰 목록 -->
	    <c:if test="${not empty product_list}">
	        <!-- 이미 처리된 쇼핑몰을 추적 -->
	        <c:set var="processedMalls" value="," />
	        
	        <c:forEach var="product" items="${product_list}">
	            <c:if test="${not empty product.product_mall_name}">
	                <!-- 중복 체크 -->
	                <c:if test="${not fn:contains(processedMalls, ','.concat(product.product_mall_name).concat(','))}">
	                    
	                    <!-- 해당 쇼핑몰 상품 개수 계산 -->
	                    <c:set var="mallCount" value="0" />
	                    <c:forEach var="countProduct" items="${product_list}">
	                        <c:if test="${countProduct.product_mall_name == product.product_mall_name}">
	                            <c:set var="mallCount" value="${mallCount + 1}" />
	                        </c:if>
	                    </c:forEach>
	                    
	                    <!-- 쇼핑몰 링크 생성 -->
	                    <div class="mall-category">
	                        <a href="productmall?product_mall_name=${product.product_mall_name}" 
	                           class="${param.mall_name == product.product_mall_name ? 'active' : ''}">
	                            ${product.product_mall_name}
	                            <span class="count">${mallCount}</span>
	                        </a>
	                    </div>
	                    
	                    <!-- 처리 완료 표시 -->
	                    <c:set var="processedMalls" value="${processedMalls}${product.product_mall_name}," />
	                </c:if>
	            </c:if>
	        </c:forEach>
	    </c:if>
	    
	    <!-- 상품이 없을 때 -->
	    <c:if test="${empty product_list}">
	        <div style="text-align: center; padding: 20px; color: #666;">
	            <p>등록된 상품이 없습니다.</p>
	        </div>
	    </c:if>
	</div>

    <!-- 메인 컨텐츠 -->
    <div class="main-content">
        <!-- 페이지 헤더 -->
        <div class="page-header">
            <h1>🏪 ProductMall</h1>
            <p>다양한 브랜드의 최고 품질 상품을 만나보세요</p>
        </div>

        <!-- 선택된 카테고리 표시 -->
        <div class="selected-category">
            <c:choose>
                <c:when test="${not empty selected_mall}">
                    <h2>${selected_mall} 상품</h2>
                    <p>${selected_mall}에서 판매하는 ${product_list != null ? product_list.size() : 0}개의 상품을 보고 있습니다.</p>
                </c:when>
                <c:otherwise>
                    <h2>전체 상품</h2>
                    <p>모든 쇼핑몰의 ${product_list != null ? product_list.size() : 0}개 상품을 보고 있습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 정렬 바 -->
        <div class="sort-bar">
            <div class="sort-left">
                <strong>${product_list != null ? product_list.size() : 0}</strong>개의 상품
            </div>
            <div class="sort-right">
                <select class="sort-select" onchange="sortProducts(this.value)">
                    <option value="latest">최신순</option>
                    <option value="price-low">가격 낮은순</option>
                    <option value="price-high">가격 높은순</option>
                    <option value="discount">할인율 높은순</option>
                    <option value="name">상품명순</option>
                </select>
            </div>
        </div>

        <!-- 상품 그리드 -->
		<div class="product-grid" id="productGrid">
		    <c:choose>
		        <c:when test="${not empty product_list}">
		            <c:forEach var="product" items="${product_list}" varStatus="status">
		                <!-- 🔧 수정1: onclick을 더 안전하게 처리 -->
		                <div class="product-card" onclick="location.href = `product_detail?product_id=${product.product_id}`">
		                    <!-- 상품 이미지 -->
		                    <div class="product-image">
		                        <c:choose>
		                            <c:when test="${not empty product.product_imgurl}">
		                                <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
		                            </c:when>
		                            <c:otherwise>
		                                <div class="no-image">📦</div>
		                            </c:otherwise>
		                        </c:choose>
		                        
		                        <!-- 할인 뱃지 -->
		                        <c:if test="${product.product_discountrate != null and product.product_discountrate > 0}">
		                            <div class="discount-badge">
		                                <fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>% OFF
		                            </div>
		                        </c:if>
		                        
		                        <!-- NEW 뱃지 -->
		                        <c:if test="${product.product_istoday == 'Y'}">
		                            <div class="new-badge">NEW</div>
		                        </c:if>
		                    </div>
		
		                    <!-- 상품 정보 -->
		                    <div class="product-info">
		                        <div class="product-mall">${product.product_mall_name}</div>
		                        <div class="product-name">${product.product_name}</div>
		                        
		                        <!-- 가격 정보 -->
		                        <div class="product-price">
		                            <c:choose>
		                                <c:when test="${product.product_discountrate != null and product.product_discountrate > 0}">
		                                    <!-- 할인 상품 -->
		                                    <div class="discount-info">
		                                        <div class="discount-rate">
		                                            <fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>%
		                                        </div>
		                                    </div>
		                                    <div class="original-price">
		                                        ₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/>
		                                    </div>
		                                    <div class="current-price">
		                                        ₩<fmt:formatNumber value="${product.product_price * (1 - product.product_discountrate)}" pattern="#,###"/>
		                                    </div>
		                                </c:when>
		                                <c:otherwise>
		                                    <!-- 일반 상품 -->
		                                    <div class="current-price">
		                                        ₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/>
		                                    </div>
		                                </c:otherwise>
		                            </c:choose>
		                        </div>
		
		                        <!-- 🔧 수정2: 액션 버튼들의 이벤트 처리 개선 -->
		                        <div class="product-actions">
		                            <button class="action-btn cart-btn" data-product-id="${product.product_id}" data-action="cart">
		                                🛒 장바구니
		                            </button>
		                            <button class="action-btn order-btn" data-product-id="${product.product_id}" data-action="order">
		                                ⚡ 바로구매
		                            </button>
		                        </div>
		                    </div>
		                </div>
		            </c:forEach>
		        </c:when>
		        <c:otherwise>
		            <!-- 빈 상태 -->
		            <div class="empty-state">
		                <i>📦</i>
		                <h3>상품이 없습니다</h3>
		                <c:choose>
		                    <c:when test="${not empty selected_mall}">
		                        <p>${selected_mall}에 등록된 상품이 없습니다.<br>다른 쇼핑몰을 선택해보세요!</p>
		                    </c:when>
		                    <c:otherwise>
		                        <p>현재 등록된 상품이 없습니다.<br>곧 다양한 상품들을 만나보실 수 있어요!</p>
		                    </c:otherwise>
		                </c:choose>
		                <a href="/shop/productmall" class="btn">전체 상품 보기</a>
		            </div>
		        </c:otherwise>
		    </c:choose>
		</div>
	</div>
</div>	

<script>
// 🔧 수정된 JavaScript 코드


// 페이지가 로딩되자마자, 버튼 클릭리스너를 적용한다?
document.addEventListener('DOMContentLoaded', function(){
	const product=document.getElementById('product');
	
	
	product.addEventListener('click', function(){
		//가까운 product-card 찾기
		const product= e.target.closest('.product-card');
		
		if(!productCard) return;
	

		//버튼을 누르면 장바구니 혹은 바로구매 호출
		
		
})

// 🔧 수정1: 이벤트 위임을 사용한 안전한 클릭 처리
document.addEventListener('DOMContentLoaded', function() {
    const productGrid = document.getElementById('productGrid');
    
    // 상품 그리드에 이벤트 위임 적용
    productGrid.addEventListener('click', function(e) {
        // 클릭된 요소에서 가장 가까운 product-card 찾기
        const productCard = e.target.closest('.product-card');
        if (!productCard) return;        

        // 버튼 클릭인지 확인
        
        if (actionBtn) {
            e.stopPropagation(); // 상품 카드 클릭 이벤트 차단
            
            const productId = actionBtn.getAttribute('data-product-id');
            const action = actionBtn.getAttribute('data-action');
            
            if (action === 'cart') {
                addToCart(productId);
            } else if (action === 'order') {
                directOrder(productId);
            }
            return;
        }
        
        // 상품 카드 클릭 (상세페이지 이동)
        const productId = productCard.getAttribute('data-product-id');
        if (productId) {
            console.log('상품 클릭됨:', productId); // 디버깅용
            goToProduct(productId);
        }
    });
    
    // 페이지 로딩 애니메이션
    const productCards = document.querySelectorAll('.product-card');
    productCards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        
        setTimeout(() => {
            card.style.transition = 'all 0.6s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 100);
    });
});

// 🔧 수정2: 상품 상세 페이지로 이동 (에러 처리 추가)
function goToProduct(productId) {
    try {
        const userId = getUserId();
        const url = `/shop/product_detail?product_id=${productId}&userid=${userId}`;
        
        console.log('이동할 URL:', url); // 디버깅용
        location.href = url;
        
    } catch (error) {
        console.error('상품 페이지 이동 오류:', error);
        // 백업 방법: 간단한 URL로 이동
        location.href = `/shop/product_detail?product_id=${productId}`;
    }
}

// 🔧 수정3: 장바구니 추가 (에러 처리 추가)
function addToCart(productId) {
    try {
        const userId = getUserId();
        
        if (confirm('장바구니에 추가하시겠습니까?')) {
            const url = `/shop/cart_write?user_id=${userId}&product_id=${productId}&cart_quantity=1`;
            console.log('장바구니 URL:', url); // 디버깅용
            location.href = url;
        }
        
    } catch (error) {
        console.error('장바구니 추가 오류:', error);
        alert('장바구니 추가 중 오류가 발생했습니다.');
    }
}

// 🔧 수정4: 바로 구매 (에러 처리 추가)
function directOrder(productId) {
    try {
        const userId = getUserId();
        const url = `/shop/order_view?product_id=${productId}&user_id=${userId}&quantity=1&form_cart=false`;
        
        console.log('주문 URL:', url); // 디버깅용
        location.href = url;
        
    } catch (error) {
        console.error('바로 구매 오류:', error);
        alert('주문 처리 중 오류가 발생했습니다.');
    }
}

// 🔧 수정5: 상품 정렬 (기존 코드 유지하되 안전성 강화)
function sortProducts(sortType) {
    try {
        const productGrid = document.getElementById('productGrid');
        const productCards = Array.from(productGrid.querySelectorAll('.product-card'));
        
        productCards.sort((a, b) => {
            switch(sortType) {
                case 'price-low':
                    const priceA = parseInt(a.querySelector('.current-price')?.textContent.replace(/[^\d]/g, '') || '0');
                    const priceB = parseInt(b.querySelector('.current-price')?.textContent.replace(/[^\d]/g, '') || '0');
                    return priceA - priceB;
                case 'price-high':
                    const priceHighA = parseInt(a.querySelector('.current-price')?.textContent.replace(/[^\d]/g, '') || '0');
                    const priceHighB = parseInt(b.querySelector('.current-price')?.textContent.replace(/[^\d]/g, '') || '0');
                    return priceHighB - priceHighA;
                case 'name':
                    const nameA = a.querySelector('.product-name')?.textContent || '';
                    const nameB = b.querySelector('.product-name')?.textContent || '';
                    return nameA.localeCompare(nameB);
                case 'discount':
                    const discountA = a.querySelector('.discount-rate')?.textContent || '0';
                    const discountB = b.querySelector('.discount-rate')?.textContent || '0';
                    return parseInt(discountB) - parseInt(discountA);
                default:
                    return 0;
            }
        });
        
        // 정렬된 순서로 다시 배치
        productCards.forEach(card => productGrid.appendChild(card));
        
    } catch (error) {
        console.error('정렬 오류:', error);
    }
}

// 🔧 디버깅을 위한 추가 함수
function debugProductClick() {
    console.log('상품 클릭 디버깅 정보:');
    console.log('- 총 상품 개수:', document.querySelectorAll('.product-card').length);
    console.log('- getUserId 함수 존재:', typeof getUserId === 'function');
    console.log('- 현재 사용자 ID:', getUserId());
    
    // 첫 번째 상품 카드에 강제로 클릭 이벤트 발생
    const firstCard = document.querySelector('.product-card');
    if (firstCard) {
        console.log('- 첫 번째 상품 ID:', firstCard.getAttribute('data-product-id'));
        // firstCard.click(); // 테스트용 (주석 처리)
    }
}

// 브라우저 콘솔에서 debugProductClick() 실행하여 디버깅 가능
</script>

</body>
</html>