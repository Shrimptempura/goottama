<%@page import="org.mariadb.jdbc.client.Context"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>상품 카테고리</title>

<style>
.main-container{
    display: flex;
    align-items: flex-start;
}

.category{
    width: 200px;
    flex-shrink: 0;
    display: flex;
    flex-direction: column;
}

.category a{
    margin-left: 50px;
    padding: 10px;
}

.card-container{
    flex-grow: 1;
    display: flex;
    flex-wrap: wrap;
    gap:20px;
}

.card {
    width: calc(25% - 20px);
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    padding: 10px;
    box-sizing: border-box;
}

.img{
    width: 150px;
    height: 150px;
}

.price-container {
    margin-top: 10px;
}

.original-price {
    text-decoration: line-through;
    color: #999;
    font-size: 14px;
}

.sale-price {
    color: #ff4444;
    font-weight: bold;
    font-size: 16px;
}

.discount-badge {
    background: #ff4444;
    color: white;
    padding: 2px 6px;
    border-radius: 3px;
    font-size: 12px;
    margin-right: 5px;
}

.category-item {
    margin-bottom: 5px;
}

.category-item > a {
    display: block;
    padding: 12px 15px;
    text-decoration: none;
    color: #333;
    font-weight: 500;
    background-color: #f8f9fa;
    border-radius: 6px;
    transition: all 0.3s ease;
    position: relative;
}

.category-item > a:hover {
    background-color: #007bff;
    color: white;
}

/* 활성화된 카테고리 스타일 */
.category-item > a.active {
    background-color: #007bff;
    color: white;
}

.sub-menu {
    margin-top: 5px;
    margin-left: 15px;
    border-left: 3px solid #007bff;
    background-color: #fff;
    border-radius: 0 6px 6px 0;
    overflow: hidden;
    transition: all 0.3s ease;
}

.sub-menu a {
    display: block;
    padding: 8px 15px;
    text-decoration: none;
    color: #666;
    font-size: 14px;
    border-bottom: 1px solid #f0f0f0;
    transition: all 0.3s ease;
}

.sub-menu a:last-child {
    border-bottom: none;
}

.sub-menu a:hover {
    background-color: #e3f2fd;
    color: #007bff;
    padding-left: 20px;
}

/* 선택된 서브메뉴 스타일 */
.sub-menu a.active {
    background-color: #007bff;
    color: white;
    font-weight: bold;
}

.arrow {
    float: right;
    transition: transform 0.3s ease;
    font-size: 12px;
}

.arrow.rotated {
    transform: rotate(180deg);
}

/* 상품 개수 표시 */
.product-count {
    margin: 20px 0;
    font-size: 16px;
    font-weight: bold;
    color: #333;
}

.category-title {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 6px;
    margin-bottom: 20px;
    border-left: 4px solid #007bff;
}
</style>

<script>
// 서버에서 전달받은 category_id (없으면 0)
var currentCategoryId = ${not empty category_id ? category_id : 0};

// 카테고리별 메뉴 매핑
var categoryMenuMapping = {
    1: {main: 'furniture', sub: 'furniture-1'},
    2: {main: 'furniture', sub: 'furniture-2'},
    3: {main: 'furniture', sub: 'furniture-3'},
    4: {main: 'electronics', sub: 'electronics-4'},
    5: {main: 'electronics', sub: 'electronics-5'},
    6: {main: 'electronics', sub: 'electronics-6'},
    7: {main: 'kitchen', sub: 'kitchen-7'},
    8: {main: 'kitchen', sub: 'kitchen-8'},
    9: {main: 'kitchen', sub: 'kitchen-9'},
    10: {main: 'light', sub: 'light-10'},
    11: {main: 'light', sub: 'light-11'},
    12: {main: 'light', sub: 'light-12'},
    13: {main: 'saving', sub: 'saving-13'},
    14: {main: 'saving', sub: 'saving-14'},
    15: {main: 'saving', sub: 'saving-15'},
    16: {main: 'living', sub: 'living-16'},
    17: {main: 'living', sub: 'living-17'},
    18: {main: 'living', sub: 'living-18'}
};

function toggleSubMenu(categoryName) {
    const subMenu = document.getElementById('sub-' + categoryName);
    const arrow = document.getElementById('arrow-' + categoryName);
    const mainLink = document.querySelector('[data-category="' + categoryName + '"]');
    
    if (subMenu.style.display === 'none' || subMenu.style.display === '') {
        // 서브메뉴 열기
        subMenu.style.display = 'block';
        arrow.textContent = '▲';
        arrow.classList.add('rotated');
        mainLink.classList.add('active');
    } else {
        // 서브메뉴 닫기
        subMenu.style.display = 'none';
        arrow.textContent = '▼';
        arrow.classList.remove('rotated');
        mainLink.classList.remove('active');
    }
   
}


function setActiveSubMenu(element, categoryId) {
    // 모든 서브메뉴에서 active 클래스 제거
    document.querySelectorAll('.sub-menu a').forEach(a => a.classList.remove('active'));
    
    // 클릭된 서브메뉴에 active 클래스 추가
    element.classList.add('active');
    
    // URL 이동
    window.location.href = 'category?category_id=' + categoryId;
}

// 페이지 로드 시 현재 카테고리에 해당하는 메뉴 활성화
document.addEventListener('DOMContentLoaded', function() {
    console.log('현재 카테고리 ID:', currentCategoryId);
    
    if (currentCategoryId > 0 && categoryMenuMapping[currentCategoryId]) {
        var mapping = categoryMenuMapping[currentCategoryId];
        
        // 메인 메뉴 활성화 및 서브메뉴 열기
        var mainCategory = mapping.main;
        toggleSubMenu(mainCategory);
        
        // 해당 서브메뉴 활성화
        var subMenuLink = document.querySelector('a[href="category?category_id=' + currentCategoryId + '"]');
        if (subMenuLink) {
            subMenuLink.classList.add('active');
        }
    }
});

function closeAllSubMenus() {
    const subMenus = document.querySelectorAll('.sub-menu');
    const arrows = document.querySelectorAll('.arrow');
    const mainLinks = document.querySelectorAll('[data-category]');
    
    subMenus.forEach(menu => menu.style.display = 'none');
    arrows.forEach(arrow => {
        arrow.textContent = '▼';
        arrow.classList.remove('rotated');
    });
    mainLinks.forEach(link => link.classList.remove('active'));
}
</script>

</head>
<body>

<div class="main-container">
    <div class="category">
        <!-- 가구 카테고리 -->
        <div class="category-item">
            <a href="#" data-category="furniture" onclick="toggleSubMenu('furniture'); return false;">
                가구 <span class="arrow" id="arrow-furniture">▼</span>
            </a>
            <div class="sub-menu" id="sub-furniture" style="display: none;">
                <a href="category?category_id=1" onclick="setActiveSubMenu(this, 1); return false;">침대</a>
                <a href="category?category_id=2" onclick="setActiveSubMenu(this, 2); return false;">소파</a>
                <a href="category?category_id=3" onclick="setActiveSubMenu(this, 3); return false;">의자</a>
            </div>
        </div>

        <!-- 가전,디지털 카테고리 -->
        <div class="category-item">
            <a href="#" data-category="electronics" onclick="toggleSubMenu('electronics'); return false;">
                가전,디지털 <span class="arrow" id="arrow-electronics">▼</span>
            </a>
            <div class="sub-menu" id="sub-electronics" style="display: none;">
                <a href="category?category_id=4" onclick="setActiveSubMenu(this, 4); return false;">냉장고</a>
                <a href="category?category_id=5" onclick="setActiveSubMenu(this, 5); return false;">세탁기,건조기</a>
                <a href="category?category_id=6" onclick="setActiveSubMenu(this, 6); return false;">청소기</a>
            </div>
        </div>

        <!-- 주방용품 카테고리 -->
        <div class="category-item">
            <a href="#" data-category="kitchen" onclick="toggleSubMenu('kitchen'); return false;">
                주방용품 <span class="arrow" id="arrow-kitchen">▼</span>
            </a>
            <div class="sub-menu" id="sub-kitchen" style="display: none;">
                <a href="category?category_id=7" onclick="setActiveSubMenu(this, 7); return false;">그릇</a>
                <a href="category?category_id=8" onclick="setActiveSubMenu(this, 8); return false;">냄비</a>
                <a href="category?category_id=9" onclick="setActiveSubMenu(this, 9); return false;">컵</a>
            </div>
        </div>

        <!-- 조명 카테고리 -->
        <div class="category-item">
            <a href="#" data-category="light" onclick="toggleSubMenu('light'); return false;">
                조명 <span class="arrow" id="arrow-light">▼</span>
            </a>
            <div class="sub-menu" id="sub-light" style="display: none;">
                <a href="category?category_id=10" onclick="setActiveSubMenu(this, 10); return false;">천장등</a>
                <a href="category?category_id=11" onclick="setActiveSubMenu(this, 11); return false;">무드등,장식조명</a>
                <a href="category?category_id=12" onclick="setActiveSubMenu(this, 12); return false;">벽조명</a>
            </div>
        </div>

        <!-- 수납,정리 카테고리 -->
        <div class="category-item">
            <a href="#" data-category="saving" onclick="toggleSubMenu('saving'); return false;">
                수납,정리 <span class="arrow" id="arrow-saving">▼</span>
            </a>
            <div class="sub-menu" id="sub-saving" style="display: none;">
                <a href="category?category_id=13" onclick="setActiveSubMenu(this, 13); return false;">빨래 바구니</a>
                <a href="category?category_id=14" onclick="setActiveSubMenu(this, 14); return false;">행거</a>
                <a href="category?category_id=15" onclick="setActiveSubMenu(this, 15); return false;">선반</a>
            </div>
        </div>

        <!-- 생활용품 카테고리 -->
        <div class="category-item">
            <a href="#" data-category="living" onclick="toggleSubMenu('living'); return false;">
                생활용품 <span class="arrow" id="arrow-living">▼</span>
            </a>
            <div class="sub-menu" id="sub-living" style="display: none;">
                <a href="category?category_id=16" onclick="setActiveSubMenu(this, 16); return false;">욕실용품</a>
                <a href="category?category_id=17" onclick="setActiveSubMenu(this, 17); return false;">청소용품</a>
                <a href="category?category_id=18" onclick="setActiveSubMenu(this, 18); return false;">세탁용품</a>
            </div>
        </div>
    </div>

    <div class="card-container">

        <!-- 상품 목록 -->
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach items="${list}" var="product">
                    <div class="card">
                        <%-- <div>상품 ID: ${product.product_id}</div> --%>
                        
                        <c:choose>
                            <c:when test="${empty product.product_img_id or empty product.product_imgurl}">
                                <img class="img" src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                            </c:when>
                            <c:otherwise>
                                <img class="img" src="/static/uploads/shop/${product.product_imgurl}" alt="상품 이미지">
                            </c:otherwise>
                        </c:choose>
                        
                        <div class="card-body">
                            <a href="product_detail?product_id=${product.product_id}">${product.product_name}</a><br/>
                            
                            <!-- 가격 표시 -->
                            <div class="price-container">
                                <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
                                
                                <c:choose>
                                    <c:when test="${hasDiscount}">
                                        <c:set var="discountPercent" value="${product.product_discountrate * 100}" />
                                        <c:set var="salePrice" value="${product.product_price - (product.product_price * product.product_discountrate)}" />
                                        
                                        <span class="discount-badge"><fmt:formatNumber value="${discountPercent}" pattern="#"/>% 할인</span><br/>
                                        <span class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span><br/>
                                        <span class="sale-price">₩<fmt:formatNumber value="${salePrice}" pattern="#,###"/></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="sale-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <!-- 카테고리 정보 표시 -->
                            <div style="margin-top: 10px; font-size: 12px; color: #666;">
                                ${product.category_main} > ${product.category_sub}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div style="width: 100%; text-align: center; padding: 50px;">
                    <h3>해당 카테고리에 상품이 없습니다.</h3>
                    <p>다른 카테고리를 선택해보세요.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>

  <%@ include file="./shoplist.jsp" %>
</html>