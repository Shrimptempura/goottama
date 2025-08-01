<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

/* 페이지 헤더 */
.page-header {
    text-align: center;
    margin-bottom: 40px;
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

/* 필터 및 정렬 바 */
.filter-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    padding: 20px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-left {
    display: flex;
    gap: 15px;
    align-items: center;
}

.filter-right {
    display: flex;
    gap: 10px;
    align-items: center;
}

.filter-btn {
    padding: 8px 16px;
    border: 2px solid #007bff;
    background: white;
    color: #007bff;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 14px;
    font-weight: 500;
}

.filter-btn:hover, .filter-btn.active {
    background: #007bff;
    color: white;
    transform: translateY(-2px);
}

.sort-select {
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
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
    color: #6c757d;
    margin-bottom: 8px;
    font-weight: 500;
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

/* 페이지네이션 */
.pagination {
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-top: 40px;
}

.pagination a {
    padding: 10px 15px;
    border: 1px solid #ddd;
    color: #007bff;
    text-decoration: none;
    border-radius: 5px;
    transition: all 0.3s ease;
}

.pagination a:hover {
    background-color: #007bff;
    color: white;
}

.pagination .active {
    background-color: #007bff;
    color: white;
    border-color: #007bff;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
    .container {
        padding: 15px;
    }
    
    .page-header h1 {
        font-size: 2rem;
    }
    
    .filter-bar {
        flex-direction: column;
        gap: 15px;
        align-items: stretch;
    }
    
    .filter-left, .filter-right {
        justify-content: center;
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
    
    .filter-left {
        flex-wrap: wrap;
    }
}

/* 로딩 애니메이션 */
.loading {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 200px;
}

.spinner {
    width: 40px;
    height: 40px;
    border: 4px solid #f3f3f3;
    border-top: 4px solid #007bff;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>
</head>
<body>

<div class="container">
    <!-- 페이지 헤더 -->
    <div class="page-header">
        <h1>🏪 ProductMall</h1>
        <p>다양한 브랜드의 최고 품질 상품을 만나보세요</p>
    </div>

    <!-- 필터 및 정렬 바 -->
    <div class="filter-bar">
        <div class="filter-left">
            <button class="filter-btn active" onclick="filterProducts('all')">전체</button>
            <button class="filter-btn" onclick="filterProducts('discount')">할인상품</button>
            <button class="filter-btn" onclick="filterProducts('new')">신상품</button>
            <button class="filter-btn" onclick="filterProducts('popular')">인기상품</button>
        </div>
        <div class="filter-right">
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
                    <div class="product-card" onclick="goToProduct(${product.product_id})">
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
                            
                            <!-- NEW 뱃지 (최근 7일 내 등록된 상품) -->
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

                            <!-- 액션 버튼들 -->
                            <div class="product-actions">
                                <button class="action-btn cart-btn" onclick="event.stopPropagation(); addToCart(${product.product_id})">
                                    🛒 장바구니
                                </button>
                                <button class="action-btn order-btn" onclick="event.stopPropagation(); directOrder(${product.product_id})">
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
                    <p>현재 등록된 상품이 없습니다.<br>곧 다양한 상품들을 만나보실 수 있어요!</p>
                    <a href="/shop/home" class="btn">홈으로 돌아가기</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    
</div>

<script>
// 상품 상세 페이지로 이동
function goToProduct(productId) {
    const userId = getUserId(); // subheader.jsp에서 정의된 함수 사용
    location.href = `/shop/product_detail?product_id=${productId}&userid=${userId}`;
}

// 장바구니에 추가
function addToCart(productId) {
    const userId = getUserId();
    
    if (confirm('장바구니에 추가하시겠습니까?')) {
        location.href = `/shop/cart_write?user_id=${userId}&product_id=${productId}&cart_quantity=1`;
    }
}

// 바로 구매
function directOrder(productId) {
    const userId = getUserId();
    location.href = `/shop/order_view?product_id=${productId}&user_id=${userId}&quantity=1&form_cart=false`;
}

// 상품 필터링
function filterProducts(type) {
    // 모든 필터 버튼에서 active 클래스 제거
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // 클릭된 버튼에 active 클래스 추가
    event.target.classList.add('active');
    
    // 실제 필터링 로직 (서버 요청 또는 클라이언트 필터링)
    const productCards = document.querySelectorAll('.product-card');
    
    productCards.forEach(card => {
        switch(type) {
            case 'all':
                card.style.display = 'block';
                break;
            case 'discount':
                const hasDiscount = card.querySelector('.discount-badge');
                card.style.display = hasDiscount ? 'block' : 'none';
                break;
            case 'new':
                const isNew = card.querySelector('.new-badge');
                card.style.display = isNew ? 'block' : 'none';
                break;
            default:
                card.style.display = 'block';
        }
    });
}

// 상품 정렬
function sortProducts(sortType) {
    const productGrid = document.getElementById('productGrid');
    const productCards = Array.from(productGrid.querySelectorAll('.product-card'));
    
    productCards.sort((a, b) => {
        switch(sortType) {
            case 'price-low':
                const priceA = parseInt(a.querySelector('.current-price').textContent.replace(/[^\d]/g, ''));
                const priceB = parseInt(b.querySelector('.current-price').textContent.replace(/[^\d]/g, ''));
                return priceA - priceB;
            case 'price-high':
                const priceHighA = parseInt(a.querySelector('.current-price').textContent.replace(/[^\d]/g, ''));
                const priceHighB = parseInt(b.querySelector('.current-price').textContent.replace(/[^\d]/g, ''));
                return priceHighB - priceHighA;
            case 'name':
                const nameA = a.querySelector('.product-name').textContent;
                const nameB = b.querySelector('.product-name').textContent;
                return nameA.localeCompare(nameB);
            default:
                return 0;
        }
    });
    
    // 정렬된 순서로 다시 배치
    productCards.forEach(card => productGrid.appendChild(card));
}

// 더 보기 (무한 스크롤 또는 페이지네이션)
function loadMore() {
    // 서버에서 추가 상품 로드
    console.log('더 많은 상품 로드...');
}

// 페이지 로딩 애니메이션
document.addEventListener('DOMContentLoaded', function() {
    // 상품 카드들에 순차적으로 나타나는 애니메이션 효과
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
</script>

</body>
</html>