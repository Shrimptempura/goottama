<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>	
<meta charset="UTF-8">
<title>쇼핑몰 홈</title>
<style>
body {

    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
}

/* 배너 섹션 */
.banner-section {
    width: 100%;
    padding: 20px 0;
    background-color: #f8f9fa;
}

.banner-container {
    width: 60%; /* 좌우 20%씩 띄우고 가운데 60% */
    margin: 0 auto;
    position: relative;
    overflow: hidden;
    border-radius: 15px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.1);
}

.banner-slider {
    display: flex;
    width: 500%; /* 5개 배너 */
    height: 300px;
    transition: transform 0.8s ease-in-out;
}

.banner-slide {
    width: 20%; /* 각 슬라이드는 전체의 1/5 */
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 28px;
    font-weight: bold;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
    flex-shrink: 0;
    cursor: pointer;
    transition: all 0.3s ease;
}

.banner-slide:hover {
    transform: scale(1.02);
    filter: brightness(1.1);
}

.banner-slide:nth-child(1) {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.banner-slide:nth-child(2) {
    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.banner-slide:nth-child(3) {
    background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.banner-slide:nth-child(4) {
    background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.banner-slide:nth-child(5) {
    background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
}

/* 배너 인디케이터 */
.banner-indicators {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    gap: 10px;
}

.indicator {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background-color: rgba(255, 255, 255, 0.5);
    cursor: pointer;
    transition: all 0.3s;
}

.indicator.active {
    background-color: white;
    transform: scale(1.2);
}

/* 배너 네비게이션 버튼 */
.banner-nav {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(255, 255, 255, 0.3);
    color: white;
    border: none;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    font-size: 20px;
    cursor: pointer;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    justify-content: center;
}

.banner-nav:hover {
    background-color: rgba(255, 255, 255, 0.5);
}

.banner-prev {
    left: 20px;
}

.banner-next {
    right: 20px;
}

/* 컨텐츠 컨테이너 */
.content-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

/* 섹션 헤더 */
.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #e9ecef;
}

.section-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
}

.more-btn {
    color: #007bff;
    text-decoration: none;
    font-weight: 500;
    padding: 8px 16px;
    border: 1px solid #007bff;
    border-radius: 20px;
    transition: all 0.3s;
}

.more-btn:hover {
    background-color: #007bff;
    color: white;
}

/* 상품 리스트 공통 스타일 */
.product-list {
    display: flex;
    gap: 20px;
    overflow-x: auto;
    padding-bottom: 10px;
    scroll-behavior: smooth;
}

.product-list::-webkit-scrollbar {
    height: 6px;
}

.product-list::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 3px;
}

.product-list::-webkit-scrollbar-thumb {
    background: #888;
    border-radius: 3px;
}

.product-list::-webkit-scrollbar-thumb:hover {
    background: #555;
}

/* 상품 카드 */
.product-card {
    min-width: 200px;
    flex-shrink: 0;
    background: white;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    overflow: hidden;
    transition: transform 0.3s, box-shadow 0.3s;
    cursor: pointer;
}

.product-card:hover {
    transform: translateY(-3px);
    box-shadow: 0 4px 15px rgba(0,0,0,0.15);
}

.product-image {
    width: 100%;
    height: 150px;
    background-color: #f8f9fa;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
    color: #6c757d;
    position: relative;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

/* 인기 배지 */
.popular-badge {
    position: absolute;
    top: 8px;
    left: 8px;
    background: #ff4444;
    color: white;
    padding: 3px 6px;
    border-radius: 8px;
    font-size: 10px;
    font-weight: bold;
}

/* 세일 배지 */
.sale-badge {
    position: absolute;
    top: 8px;
    left: 8px;
    background: #28a745;
    color: white;
    padding: 3px 6px;
    border-radius: 8px;
    font-size: 10px;
    font-weight: bold;
}

.product-info {
    padding: 12px;
}

.product-name {
    font-size: 14px;
    font-weight: 600;
    color: #333;
    margin-bottom: 6px;
    line-height: 1.3;
    height: 36px;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}

.product-mall {
    font-size: 11px;
    color: #666;
    margin-bottom: 6px;
}

.product-price {
    display: flex;
    align-items: center;
    gap: 6px;
    flex-wrap: wrap;
}

.discount-rate {
    color: #dc3545;
    font-weight: bold;
    font-size: 12px;
}

.original-price {
    color: #999;
    text-decoration: line-through;
    font-size: 12px;
}

.current-price {
    color: #333;
    font-weight: bold;
    font-size: 14px;
}

/* 섹션 간격 */
.section {
    margin-bottom: 50px;
}

/* 반응형 */
@media (max-width: 768px) {
    .banner-container {
        width: 90%;
    }
    
    .main-banner {
        height: 200px;
        font-size: 20px;
    }
    
    .content-container {
        padding: 20px 10px;
    }
    
    .product-card {
        min-width: 160px;
    }
    
    .section-title {
        font-size: 20px;
    }
    
    .product-name {
        font-size: 13px;
        height: 32px;
    }
    
    .product-info {
        padding: 10px;
    }
}
</style>
</head>
<body>

<!-- 배너 섹션 -->
<div class="banner-section">
    <div class="banner-container">
        <div class="banner-slider" id="bannerSlider">
            <div class="banner-slide" onclick="goToEventPage('special-discount')">
                🛍️ 특별 할인 이벤트 진행중!
            </div>
            <div class="banner-slide" onclick="goToEventPage('new-product')">
                🎉 신상품 출시 기념 50% 할인!
            </div>
            <div class="banner-slide" onclick="goToEventPage('free-shipping')">
                🚚 무료배송 + 당일배송 서비스!
            </div>
            <div class="banner-slide" onclick="goToEventPage('card-discount')">
                💳 카드 결제 시 추가 10% 할인!
            </div>
            <div class="banner-slide" onclick="goToEventPage('signup-coupon')">
                🎁 회원가입 시 5만원 쿠폰 증정!
            </div>
        </div>
        
        <!-- 네비게이션 버튼 -->
        <button class="banner-nav banner-prev" onclick="prevSlide()">‹</button>
        <button class="banner-nav banner-next" onclick="nextSlide()">›</button>
        
        <!-- 인디케이터 -->
        <div class="banner-indicators">
            <span class="indicator active" onclick="goToSlide(0)"></span>
            <span class="indicator" onclick="goToSlide(1)"></span>
            <span class="indicator" onclick="goToSlide(2)"></span>
            <span class="indicator" onclick="goToSlide(3)"></span>
            <span class="indicator" onclick="goToSlide(4)"></span>
        </div>
    </div>
</div>

<!-- 메인 컨텐츠 -->
<div class="content-container">
    
    <!-- 인기상품 섹션 -->
    <div class="section">
        <div class="section-header">
            <h2 class="section-title">🔥 인기상품</h2>
            <a href="#" class="more-btn" onclick="goToMorePage('popular'); return false;">더보기</a>
        </div>
        <div class="product-list" id="popularProducts">
            <!-- 인기상품 데이터가 있는 경우 -->
            <c:choose>
                <c:when test="${not empty popularProducts}">
                    <c:forEach var="product" items="${popularProducts}" varStatus="status">
                        <c:if test="${status.index < 8}"> <!-- 최대 8개까지 -->
                            <div class="product-card" onclick="location.href = `product_detail?product_id=${product.product_id}`">
                                <div class="product-image">
                                    <div class="popular-badge">인기</div>
                                    <c:choose>
                                        <c:when test="${not empty product.product_imgurl}">
                                            <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product-info">
                                    <div class="product-mall">${product.product_mall_name}</div>
                                    <div class="product-name">${product.product_name}</div>
                                    <div class="product-price">
                                        <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
                                        <c:choose>
                                            <c:when test="${hasDiscount}">
                                                <span class="discount-rate"><fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>%</span>
                                                <span class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                                <span class="current-price">₩<fmt:formatNumber value="${product.product_price * (1 - product.product_discountrate)}" pattern="#,###"/></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="current-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- 임시 인기상품 데이터 -->
                    <c:forEach var="product" items="${list}" varStatus="status" begin="0" end="3">
                        <div class="product-card" onclick="product_detail?product_id=${product.product_id}">
                            <div class="product-image">
                                <div class="popular-badge">인기</div>
                                <c:choose>
                                    <c:when test="${not empty product.product_imgurl}">
                                        <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="product-info">
                                <div class="product-mall">${product.product_mall_name}</div>
                                <div class="product-name">${product.product_name}</div>
                                <div class="product-price">
                                    <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
                                    <c:choose>
                                        <c:when test="${hasDiscount}">
                                            <span class="discount-rate"><fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>%</span>
                                            <span class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                            <span class="current-price">₩<fmt:formatNumber value="${product.product_price * (1 - product.product_discountrate)}" pattern="#,###"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="current-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 세일상품 섹션 -->
    <div class="section">
        <div class="section-header">
            <h2 class="section-title">💥 세일상품</h2>
            <a href="#" class="more-btn" onclick="goToMorePage('sale'); return false;">더보기</a>
        </div>
        <div class="product-list" id="saleProducts">
            <!-- 세일상품 데이터가 있는 경우 -->
            <c:choose>
                <c:when test="${not empty saleProducts}">
                    <c:forEach var="product" items="${saleProducts}" varStatus="status">
                        <c:if test="${status.index < 8}"> <!-- 최대 8개까지 -->
                            <div class="product-card" onclick="location.href = `product_detail?product_id=${product.product_id}`">
                                <div class="product-image">
                                    <div class="sale-badge">SALE</div>
                                    <c:choose>
                                        <c:when test="${not empty product.product_imgurl}">
                                            <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product-info">
                                    <div class="product-mall">${product.product_mall_name}</div>
                                    <div class="product-name">${product.product_name}</div>
                                    <div class="product-price">
                                        <span class="discount-rate"><fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>%</span>
                                        <span class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                        <span class="current-price">₩<fmt:formatNumber value="${product.product_price * (1 - product.product_discountrate)}" pattern="#,###"/></span>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- 임시 세일상품 데이터 (할인율이 있는 상품들) -->
                    <c:forEach var="product" items="${list}" varStatus="status">
                        <c:if test="${product.product_discountrate != null and product.product_discountrate > 0 and status.index < 4}">
                            <div class="product-card" onclick="goToProductDetail('${product.product_id}')">>
                                <div class="product-image">
                                    <div class="sale-badge">SALE</div>
                                    <c:choose>
                                        <c:when test="${not empty product.product_imgurl}">
                                            <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product-info">
                                    <div class="product-mall">${product.product_mall_name}</div>
                                    <div class="product-name">${product.product_name}</div>
                                    <div class="product-price">
                                        <span class="discount-rate"><fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>%</span>
                                        <span class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                        <span class="current-price">₩<fmt:formatNumber value="${product.product_price * (1 - product.product_discountrate)}" pattern="#,###"/></span>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>

<script>


// 배너 슬라이더 변수
let currentSlide = 0;
const slides = document.querySelectorAll('.banner-slide');

const totalSlides = 5;
let slideInterval;

// 배너 클릭 시 페이지 이동 함수
function goToEventPage(eventType) {
    let url = '';
    
    switch(eventType) {
        case 'special-discount':
            url = 'best';
            break;
        case 'new-product':
            url = '/shop/products/new';
            break;
        case 'free-shipping':
            url = '/shop/events/free-shipping';
            break;
        case 'card-discount':
            url = '/shop/events/card-discount';
            break;
        case 'signup-coupon':
            url = '/user/signup';
            break;
        default:
            url = '/shop/events';
    }
    
    // 페이지 이동
    window.location.href = url;
}

//상품 하이퍼링크
function goToProductDetail(product_id) {
   location.href = `product_detail?product_id=${product.product_id}`;
}


// 배너 슬라이더 함수
function updateSlider() {
    const slider = document.getElementById('bannerSlider');
    const indicators = document.querySelectorAll('.indicator');
    
    // 슬라이더 이동 (이 부분이 빠져있었음)
    slider.style.transform = `translateX(-${currentSlide * 20}%)`;
    
    // 인디케이터 업데이트
    indicators.forEach((indicator, index) => {
        indicator.classList.toggle('active', index === currentSlide);
    });
}

function nextSlide() {
    currentSlide = (currentSlide + 1) % totalSlides;
    updateSlider();
}

function prevSlide() {
    currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
    updateSlider();
}

function goToSlide(slideIndex) {
    currentSlide = slideIndex;
    updateSlider();
}

// 자동 슬라이드 (5초마다)
function startAutoSlide() {
    slideInterval = setInterval(nextSlide, 5000);
}

function stopAutoSlide() {
    clearInterval(slideInterval);
}

// 마우스 호버 시 자동 슬라이드 정지
function initializeBannerEvents() {
    const bannerContainer = document.querySelector('.banner-container');
    
    bannerContainer.addEventListener('mouseenter', stopAutoSlide);
    bannerContainer.addEventListener('mouseleave', startAutoSlide);
}

// 페이지 로드 시 초기화
function initializePage() {
    initializeBannerEvents();
    startAutoSlide();
    initializeProductScrolls();
}

// 상품 리스트 스크롤 초기화
function initializeProductScrolls() {
    // 가로 스크롤 기능 (마우스 휠로 좌우 스크롤)
    document.querySelectorAll('.product-list').forEach(list => {
        list.addEventListener('wheel', function(e) {
            if (e.deltaY !== 0) {
                e.preventDefault();
                this.scrollLeft += e.deltaY;
            }
        });
    });

    // 터치 스크롤 지원 (모바일)
    let isDown = false;
    let startX;
    let scrollLeft;

    document.querySelectorAll('.product-list').forEach(list => {
        list.addEventListener('mousedown', (e) => {
            isDown = true;
            startX = e.pageX - list.offsetLeft;
            scrollLeft = list.scrollLeft;
        });
        
        list.addEventListener('mouseleave', () => {
            isDown = false;
        });
        
        list.addEventListener('mouseup', () => {
            isDown = false;
        });
        
        list.addEventListener('mousemove', (e) => {
            if (!isDown) return;
            e.preventDefault();
            const x = e.pageX - list.offsetLeft;
            const walk = (x - startX) * 2;
            list.scrollLeft = scrollLeft - walk;
        });
    });
}


// 더보기 페이지로 이동
function goToMorePage(pageType) {
    let url = '';
    
    switch(pageType) {
        case 'popular':
            url = '/shop/product_popular';
            break;
        case 'sale':
            url = '/shop/product_high_sales';
            break;
        default:
            url = '/shop/products';
    }
    
    window.location.href = url;
}

// 페이지 로드 시 모든 기능 초기화
window.addEventListener('load', initializePage);

// DOM이 완전히 로드되었을 때도 실행 (안전장치)
document.addEventListener('DOMContentLoaded', function() {
    if (document.readyState === 'complete') {
        initializePage();
    }
});
</script>

</body>
 <%@ include file="./shoplist.jsp" %>
</html>