<%@page import="org.mariadb.jdbc.client.Context"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>

.main-container{
display: flex;
align-items: flex-start;
}

.category{
width: 200px;
flex-shrink: 0; /* 작아지지 않도록 */
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
width: calc(25% - 20px); /* 4칸 (100% / 4 - gap) */
box-shadow: 0 2px 6px rgba(0,0,0,0.1);
padding: 10px;
box-sizing: border-box;
}

.img{
width: 150px;
height: 150px;
}

/* 가격 스타일 추가 */
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

.category {
    width: 200px;
    flex-shrink: 0;
    display: flex;
    flex-direction: column;
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

/* 서브메뉴 스타일 */
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

/* 화살표 */
.arrow {
    float: right;
    transition: transform 0.3s ease;
    font-size: 12px;
}

.arrow.rotated {
    transform: rotate(180deg);
}

</style>

<script>

var category_id=${category_id != null ? category_id:0};


function toggleSubMenu(categoryName) {
    const subMenu = document.getElementById('sub-' + categoryName);
    const arrow = document.getElementById('arrow-' + categoryName);
    //카테고리 아이디를 받아서 카테고리 서브메뉴가 열려있고 파란색이어야함
    var category_id=${category_id};
    
    if (subMenu.style.display === 'none' || subMenu.style.display === '') {
        // 서브메뉴 열기
        subMenu.style.display = 'block';
        arrow.textContent = '▲';
        arrow.classList.add('rotated');
    	
        var category_id=${category_id};
        //파란새ㄱ이며 열려있게 하기 
        if(subMenu==category_id){
			
	        subMenu.style.color='blue';
	        subMenu.style.display = 'block';
        }
        
        category_id
        
    } else {
        // 서브메뉴 닫기
        subMenu.style.display = 'none';
        arrow.textContent = '▼';
        arrow.classList.remove('rotated');
    }
}

// 다른 메뉴 클릭 시 열린 메뉴 닫기 (선택사항)
function closeAllSubMenus() {
    const subMenus = document.querySelectorAll('.sub-menu');
    const arrows = document.querySelectorAll('.arrow');
    
    subMenus.forEach(menu => menu.style.display = 'none');
    arrows.forEach(arrow => {
        arrow.textContent = '▼';
        arrow.classList.remove('rotated');
    });
}
</script>

</head>
<body>
<h2>category</h2>

<div class="main-container">

    <div class="category">
	    <!-- 가구 카테고리 -->
	    <div class="category-item">
	        <a href="#" onclick="toggleSubMenu('furniture'); return false;">
	            가구 <span class="arrow" id="arrow-furniture">▼</span>
	        </a>
	        <div class="sub-menu" id="sub-furniture" style="display: none;">
	            <a href="category?category_id=1" onclick="setActiveSubMenu(this, 11); return false;">침대</a>
	            <a href="category?category_id=2" onclick="setActiveSubMenu(this, 12); return false;">소파</a>
	            <a href="category?category_id=3" onclick="setActiveSubMenu(this, 13); return false;">의자</a>
	        </div>
	    </div>
	
	    <!-- 가전,디지털 카테고리 -->
	    <div class="category-item">
	        <a href="#" onclick="toggleSubMenu('electronics'); return false;">
	            가전,디지털 <span class="arrow" id="arrow-electronics">▼</span>
	        </a>
	        <div class="sub-menu" id="sub-electronics" style="display: none;">
	            <a href="category?category_id=4" onclick="setActiveSubMenu(this, 21); return false;">냉장고</a>
	            <a href="category?category_id=5" onclick="setActiveSubMenu(this, 22); return false;">세탁기,건조기</a>
	            <a href="category?category_id=6" onclick="setActiveSubMenu(this, 23); return false;">청소기</a>
	        </div>
	    </div>
	
	    <!-- 주방용품 카테고리 -->
	    <div class="category-item">
	        <a href="#" onclick="toggleSubMenu('kitchen'); return false;">
	            주방용품 <span class="arrow" id="arrow-kitchen">▼</span>
	        </a>
	        <div class="sub-menu" id="sub-kitchen" style="display: none;">
	            <a href="category?category_id=7" onclick="setActiveSubMenu(this, 31); return false;">그릇</a>
	            <a href="category?category_id=8" onclick="setActiveSubMenu(this, 32); return false;">냄비</a>
	            <a href="category?category_id=9" onclick="setActiveSubMenu(this, 33); return false;">컵</a>
	        </div>
	    </div>
	
	    <!-- 조명 카테고리 -->
	    <div class="category-item">
	        <a href="#" onclick="toggleSubMenu('light'); return false;">
	            조명 <span class="arrow" id="arrow-light">▼</span>
	        </a>
	        <div class="sub-menu" id="sub-light" style="display: none;">
	            <a href="category?category_id=10" onclick="setActiveSubMenu(this, 41); return false;">천장등</a>
	            <a href="category?category_id=11" onclick="setActiveSubMenu(this, 42); return false;">무드등,장식조명</a>
	            <a href="category?category_id=12" onclick="setActiveSubMenu(this, 43); return false;">벽조명</a>
	        </div>
	    </div>
	
	    <!-- 수납,정리 카테고리 -->
	    <div class="category-item">
	        <a href="#" onclick="toggleSubMenu('saving'); return false;">
	            수납,정리 <span class="arrow" id="arrow-saving">▼</span>
	        </a>
	        <div class="sub-menu" id="sub-saving" style="display: none;">
	            <a href="category?category_id=13" onclick="setActiveSubMenu(this, 51); return false;">빨래 바구니</a>
	            <a href="category?category_id=14" onclick="setActiveSubMenu(this, 52); return false;">행거</a>
	            <a href="category?category_id=15" onclick="setActiveSubMenu(this, 53); return false;">선반</a>
	        </div>
	    </div>
	
	    <!-- 생활용품 카테고리 -->
	    <div class="category-item">
	        <a href="#" onclick="toggleSubMenu('living'); return false;">
	            생활용품 <span class="arrow" id="arrow-living">▼</span>
	        </a>
	        <div class="sub-menu" id="sub-living" style="display: none;">
	            <a href="category?category_id=16" onclick="setActiveSubMenu(this, 61); return false;">욕실용품</a>
	            <a href="category?category_id=17" onclick="setActiveSubMenu(this, 62); return false;">청소용품</a>
	            <a href="category?category_id=18" onclick="setActiveSubMenu(this, 63); return false;">세탁용품</a>
	        </div>
	    </div>
	</div>


<div class="card-container">
		
	<c:forEach items="${list }" var="product">
		<div class="card">
		<div>${product.product_id }</div>
		
			<c:choose>
				<c:when test="${empty product.product_img_id or empty product.product_imgurl}">
					<img class="img" src="/static/uploads/shop/noimages.png" alt="기본 이미지" style="width:150px;">
				</c:when>
				<c:otherwise>
					<img class="img" src="/static/uploads/shop/${product.product_imgurl}" alt="상품 이미지" style="width:150px;">
				</c:otherwise>
			</c:choose>
		
			<div class="card-body">
				<a href="product_detail?product_id=${product.product_id }">${product.product_name}</a> <br />
				
				<!-- 가격 표시 (할인가 적용) -->
				<div class="price-container">
				    <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
				    
				    <c:choose>
				        <c:when test="${hasDiscount}">
				            <!-- 할인이 있는 경우 -->
				            <c:set var="discountPercent" value="${product.product_discountrate * 100}" />
				            <c:set var="salePrice" value="${product.product_price - (product.product_price * product.product_discountrate)}" />
				            
				            <span class="discount-badge"><fmt:formatNumber value="${discountPercent}" pattern="#"/>% 할인</span><br />
				            <span class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span><br />
				            <span class="sale-price">₩<fmt:formatNumber value="${salePrice}" pattern="#,###"/></span>
				        </c:when>
				        <c:otherwise>
				            <!-- 할인이 없는 경우 -->
				            <span class="sale-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></span>
				        </c:otherwise>
				    </c:choose>
				</div>
				<!--  -->
			</div>
		</div>
	</c:forEach>

</div>

</div>

</body>
</html>	   