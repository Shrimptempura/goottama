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
</head>

<style>
.main-container{
  	display: flex;
  	gap: 20px;
  	padding: 20px;
  	max-width: 1200px;
  	margin: 0 auto;
  	align-items: flex-start; /* 상단 정렬로 겹침 방지 */
}

.left-section{
	flex: 1;
	display: flex;
	flex-direction: column;
	min-width: 0; /* flex 아이템이 너무 작아지지 않게 */
}

.right-section{
	width: 350px;
	min-width: 350px; /* 최소 너비 보장 */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	border: 2px solid pink;
	padding: 20px;
	height: fit-content;
	position: sticky;
	top: 20px;
	flex-shrink: 0; /* 크기 축소 방지 */
}

.imgcontainer {
	display: flex;
	gap: 20px;
	margin-bottom: 30px;
}

.thumbnail-column {
    display: flex;
    flex-direction: column;
    gap: 10px;
    width: 100px;
    flex-shrink: 0; /* 썸네일 크기 고정 */
}

.thumbnail {
    width: 80px;
    height: 80px;
    border: 2px solid #ccc;
    border-radius: 8px;
    cursor: pointer;
    object-fit: cover;
}

.thumbnail:hover {
    border-color: #007bff;
}

.main-image-container {
    flex: 1;
    max-width: calc(100% - 120px); /* 썸네일 공간 제외 */
}

.main-img{
	width: 100%;
	max-width: 500px;
	height: 500px;
	background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 10px;
    padding: 15px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    box-sizing: border-box;
    object-fit: cover;
}

.bottombar{
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 30px;
    background-color: #fff;
}

.bottombar ul{
	list-style: none;
	padding: 0;
	margin: 0;
	display: flex;
	gap: 40px;
	flex-wrap: wrap; /* 작은 화면에서 줄바꿈 */
}

.bottombar a{
    text-decoration: none;
    color: #333;
    font-weight: bold;
    padding: 10px 15px;
    border-radius: 5px;
    transition: background-color 0.3s;
}

.bottombar a:hover{
	background-color: #f8f9fa;
}

.productinfo{
	width: 100%;
	padding: 30px;
	background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.productinfo h3{
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 18px;
}

.productinfo div{
	margin-bottom: 10px;
	padding: 8px 0;
	border-bottom: 1px solid #eee;
}

.review-section {
    width: 100%;
    padding: 30px;
    background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.review-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 25px;
    padding-bottom: 15px;
    border-bottom: 2px solid #eee;
}

.review-title {
    margin: 0;
    font-size: 20px;
    color: #333;
}

.review-write-btn {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    font-size: 14px;
}

.review-write-btn:hover {
    background-color: #0056b3;
}

.review-item {
    margin-bottom: 25px;
    padding: 20px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    background-color: #fafafa;
}

.review-item:last-child {
    margin-bottom: 0;
}

.review-content-area {
    width: 100%;
}

.review-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.review-item-title {
    margin: 0;
    font-size: 16px;
    font-weight: bold;
    color: #333;
    flex: 1;
}

.review-author {
    font-size: 14px;
    color: #666;
    font-weight: normal;
    margin-left: 20px;
}

.review-bottom {
    display: flex;
    gap: 20px;
    align-items: flex-start;
}

.review-image {
    flex-shrink: 0;
    width: 150px;
    height: 150px;
}

.review-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    border: 1px solid #ddd;
}

.review-text {
    flex: 1;
    padding: 10px 0;
}

.review-text p {
    margin: 0;
    line-height: 1.6;
    color: #333;
    font-size: 14px;
}

.title{
	font-size: 24px; 
	font-weight: bold;
	margin-bottom: 15px;
}

.mallname{
	color: #666;
	font-size: 16px;
	margin-bottom: 10px;
}

.discountrate{
	color: red;
	font-weight: bold;
	font-size: 18px;
	margin-bottom: 5px;
}

.original-price{
	text-decoration: line-through;
	color: #999;
	font-size: 16px;
	margin-bottom: 5px;
}

.sale-price{
	color: #ff4444;
	font-weight: bold;
	font-size: 24px;
	margin-bottom: 20px;
}

.quantity-controls{
	display: flex;
	align-items: center;
	gap: 10px;
	margin-bottom: 20px;
}

.quantity-controls button{
	width: 30px;
	height: 30px;
	border: 1px solid #ddd;
	background: #f8f9fa;
	cursor: pointer;
	border-radius: 3px;
}

.quantity-controls span{
	min-width: 30px;
	text-align: center;
	font-weight: bold;
}

.action-buttons{
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.action-buttons button{
	padding: 12px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-weight: bold;
	font-size: 16px;
}

.cart-btn{
	background: #28a745;
	color: white;
}

.order-btn{
	background: #007bff;
	color: white;
}

.action-buttons button:hover{
	opacity: 0.9;
}

/* 반응형 대응 */
@media (max-width: 768px) {
    .main-container {
        flex-direction: column;
        gap: 20px;
    }
    
    .right-section {
        width: 100%;
        min-width: auto;
        position: relative;
        top: 0;
    }
    
    .imgcontainer {
        flex-direction: column;
        align-items: center;
    }
    
    .thumbnail-column {
        flex-direction: row;
        width: 100%;
        justify-content: center;
    }
    
    .main-image-container {
        max-width: 100%;
    }
    
    .main-img {
        max-width: 400px;
        height: 400px;
    }
    
    .bottombar ul {
        justify-content: center;
        gap: 20px;
    }
}

<!-- 상품 문의 -->
.inquiry-section {
    width: 100%;
    padding: 30px;
    background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.inquiry-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 25px;
    padding-bottom: 15px;
    border-bottom: 2px solid #eee;
}

.inquiry-title {
    margin: 0;
    font-size: 20px;
    color: #333;
}

.inquiry-write-btn {
    background-color: #28a745;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    font-size: 14px;
}

.inquiry-write-btn:hover {
    background-color: #218838;
}

.inquiry-item {
    margin-bottom: 25px;
    padding: 20px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    background-color: #fafafa;
}

.inquiry-item:last-child {
    margin-bottom: 0;
}

.inquiry-content-area {
    width: 100%;
}

.inquiry-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.inquiry-item-title {
    margin: 0;
    font-size: 16px;
    font-weight: bold;
    color: #333;
    flex: 1;
}

.inquiry-meta {
    display: flex;
    gap: 15px;
    align-items: center;
}

.inquiry-author {
    font-size: 14px;
    color: #666;
    font-weight: normal;
}

.inquiry-date {
    font-size: 12px;
    color: #999;
}

.inquiry-content {
    margin-bottom: 15px;
}

.inquiry-text {
    margin: 0;
    line-height: 1.6;
    color: #333;
    font-size: 14px;
    padding: 10px 0;
}

.inquiry-image {
    margin-top: 10px;
}

.inquiry-image img {
    max-width: 200px;
    max-height: 200px;
    object-fit: cover;
    border-radius: 8px;
    border: 1px solid #ddd;
}

.secret-inquiry {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 15px;
    background-color: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 6px;
    color: #6c757d;
    font-style: italic;
}

.lock-icon {
    font-size: 16px;
}

.inquiry-answer {
    background-color: #e8f5e8;
    border: 1px solid #c3e6c3;
    border-radius: 6px;
    padding: 15px;
    margin-top: 15px;
}

.answer-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}

.answer-label {
    font-weight: bold;
    color: #28a745;
    font-size: 14px;
}

.answer-date {
    font-size: 12px;
    color: #666;
}

.answer-content p {
    margin: 0;
    line-height: 1.6;
    color: #333;
    font-size: 14px;
}

.inquiry-status {
    margin-top: 15px;
    padding: 8px 15px;
    background-color: #fff3cd;
    border: 1px solid #ffeaa7;
    border-radius: 6px;
}

.status-waiting {
    color: #856404;
    font-size: 14px;
    font-weight: bold;
}

.no-inquiry {
    text-align: center;
    padding: 40px 20px;
    color: #666;
}

.no-inquiry-content p {
    margin: 10px 0;
    font-size: 16px;
}

.no-inquiry-content p:first-child {
    font-size: 18px;
    font-weight: bold;
    color: #333;
}

/* 반응형 대응 */
@media (max-width: 768px) {
    .inquiry-top {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
    }
    
    .inquiry-meta {
        flex-direction: column;
        gap: 5px;
        align-items: flex-start;
    }
    
    .inquiry-header {
        flex-direction: column;
        gap: 15px;
        align-items: stretch;
    }
    
    .inquiry-write-btn {
        width: 100%;
    }
}
/* 문의 관련 추가 스타일 */
.inquiry-top {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 15px;
}

.inquiry-actions {
    display: flex;
    gap: 8px;
    flex-shrink: 0;
}

.btn-edit, .btn-delete {
    padding: 6px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-weight: bold;
    transition: all 0.3s;
}

.btn-edit {
    background: #28a745;
    color: white;
}

.btn-edit:hover {
    background: #218838;
}

.btn-delete {
    background: #dc3545;
    color: white;
}

.btn-delete:hover {
    background: #c82333;
}

.my-inquiry {
    background-color: #e3f2fd !important;
    border-left: 4px solid #2196f3 !important;
}

.my-inquiry-text {
    color: #2196f3;
    font-weight: bold;
    font-size: 12px;
    margin: 0;
}

/* 반응형 대응 */
@media (max-width: 768px) {
    .inquiry-top {
        flex-direction: column;
        gap: 10px;
    }
    
    .inquiry-actions {
        align-self: flex-end;
    }
    
    .btn-edit, .btn-delete {
        padding: 8px 16px;
        font-size: 14px;
    }
}

/* 배송 환불  */

.deliver-refund-section{
	max-width: 800px;
    margin: 0 auto;
    border: 1px solid gray;
    border-radius: 10px;
    padding: 20px;
    background-color: white;
}

.deliver-section{
	margin: 0 auto;
 	border: 1px solid gray;
 	border-radius: 10px;
 	padding: 20px;
 	background-color: white;
}

.exchange-section{
	margin: 0 auto;
 	border: 1px solid gray;
 	border-radius: 10px;
 	padding: 20px;
 	background-color: white;
}

.deliver-info{
	margin: 0 auto;
 	border: 1px solid gray;
 	border-radius: 10px;
 	padding: 20px;
 	background-color: white;
}



</style>

<script>
//사진 클릭 시 이미지 전환
function changeMainImage(element){
	const src= element.getAttribute("src");
	document.getElementById("main=img").setAttribute("src", src);
}


//========== 누락된 함수들 추가 ==========
function changeMainImage(thumbnailElement) {
    const newSrc = thumbnailElement.getAttribute("src");
    document.getElementById("mainImage").setAttribute("src", newSrc);
}

let count = 1;
function changeCount(value){
    count += value;
    if (count < 1) count = 1;
    document.getElementById("count").innerText = count;
}

// ========== getuserid() 함수 정의 ==========
function getuserid() {
    var userId = ${loginMember.user_id};
    if (!userId || userId.trim() === '' || userId === 'null') {
        userId = '1'; // 기본값으로 1 사용
    }
    return userId;
}

// ========== 기존 함수들 수정 ==========
function showAlert(){
    let userId = getuserid();
    alert("장바구니에 담았습니다.");
    location.href = "cart_write?user_id=" + userId + "&product_id=${product.product_id}&cart_quantity=" + count;
}

function reviewwrite() {
    var userId = getuserid();
    // 리뷰 작성 페이지로 이동
    location.href = "review_write_view?user_id=" + userId + "&product_id=${product.product_id}";
}

function inquirywrite() {
    var userId = getuserid();
    // 문의 작성 페이지로 이동
    location.href = "product_inquiry_write_view?user_id=" + userId + "&product_id=${product.product_id}";
}

// 바로 주문하기도 getuserid() 사용하도록 수정
function goorder(){
    let userId = ${loginMember.user_id};
    // 바로 주문: product_id, user_id, quantity 전달
    location.href = 'order_view?product_id=${product.product_id}' + 
                   '&user_id=' + userId + 
                   '&quantity=' + count+
                   '&form_cart=false';
}

// ========== 문의 관련 새로운 함수들 ==========
// 페이지 로드 시 문의 항목들 확인
function checkMyInquiries() {
    var currentUserId = getuserid();
    console.log('현재 사용자 ID:', currentUserId);
    
    // 모든 문의 아이템 확인
    document.querySelectorAll('.inquiry-item').forEach(function(item) {
        var inquiryUserId = item.dataset.userId;
        
        console.log('문의 확인:', {
            inquiryId: item.dataset.inquiryId,
            inquiryUserId: inquiryUserId,
            currentUserId: currentUserId,
            isMatch: currentUserId === inquiryUserId
        });
        
        if (currentUserId === inquiryUserId) {
            // 내 문의인 경우
            console.log('내 문의 발견:', item.dataset.inquiryId);
            
            // 내 문의 표시
            var myInquirySection = item.querySelector('.my-inquiry-section');
            if (myInquirySection) {
                myInquirySection.style.display = 'block';
                console.log('내 문의 섹션 표시됨');
            }
            
            // 수정/삭제 버튼 표시 (오른쪽에)
            var actions = item.querySelector('.inquiry-actions');
            if (actions) {
                actions.style.display = 'flex';
                console.log('수정/삭제 버튼 표시됨');
            }
            
            // 내 문의 스타일 적용
            item.classList.add('my-inquiry');
            console.log('내 문의 스타일 적용됨');
        } else {
            console.log('다른 사용자의 문의:', inquiryUserId);
        }
    });
}

// 문의 수정
function editInquiry(inquiryId) {
    var userId = getuserid();
    console.log('문의 수정:', inquiryId, 'by user:', userId);
    location.href = 'inquiry_edit_view?inquiry_id=' + inquiryId + '&user_id=' + userId;
}

// 문의 삭제
function deleteInquiry(inquiryId) {
    if (confirm('정말 이 문의를 삭제하시겠습니까?')) {
        var userId = getuserid();
        console.log('문의 삭제:', inquiryId, 'by user:', userId);
        location.href = 'inquiry_delete?inquiry_id=' + inquiryId + '&user_id=' + userId;
    }
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function() {
    console.log('페이지 로드 완료, 문의 확인 시작');
    checkMyInquiries();
});

// 디버깅용 함수
function debugInquiries() {
    var currentUserId = getuserid();
    console.log('=== 문의 디버깅 ===');
    console.log('현재 사용자 ID:', currentUserId);
    
    document.querySelectorAll('.inquiry-item').forEach(function(item, index) {
        console.log(`문의 ${index + 1}:`, {
            inquiryId: item.dataset.inquiryId,
            userId: item.dataset.userId,
            isMyInquiry: currentUserId === item.dataset.userId
        });
    });
}

//bottom bar
//부드러운 스크롤
// 부드러운 스크롤 효과를 위한 추가 스크립트 (CSS scroll-behavior로도 충분함)
        document.querySelectorAll('.bottombar a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });
        
       

</script>


<body>
<h2>product_detail</h2>

<div class="main-container">

     

    <!-- 왼쪽 섹션: 이미지, 하단바, 상품정보 -->
    <div class="left-section">
    	
        <!-- 이미지 영역 -->
        <div class="imgcontainer">
            <!-- 왼쪽 썸네일 리스트 -->
            <div class="thumbnail-column">
                <c:forEach var="productimg" items="${productimgs}">
                    <img class="thumbnail" src="/static/uploads/shop/${productimg.product_imgurl}" alt="썸네일 이미지" onclick="changeMainImage(this)"/>
                </c:forEach>
            </div>

            <!-- 메인 이미지 -->
            <div class="main-image-container">
                <c:if test="${not empty productimgs}">
                    <img id="mainImage" class="main-img" src="/static/uploads/shop/${productimgs[0].product_imgurl}" alt="대표 이미지" />
                </c:if>
                <c:if test="${empty productimgs}">
                    <img id="mainImage" class="main-img" src="/static/uploads/shop/noimages.png" alt="기본 이미지" />
                </c:if>
            </div>
        </div>

        <!-- 하단바 (이미지 아래) -->
        <div class="bottombar">
            <ul>
                <li><a href="#productinfo">상품정보</a></li>
                <li><a href="#review-section">리뷰</a></li>
                <li><a href="#inquiry-section">문의</a></li>
                <li><a href="#">배송/환불</a></li>
            </ul>
        </div>

        <!-- 상품 상세 정보 (하단바 아래) -->
        <div id="productinfo" class="productinfo">
            <h3>상품 정보제공고시</h3>
            <div><strong>제조국:</strong> ${product.product_madein}</div>
            <div><strong>출시 일자:</strong> <fmt:formatDate value="${product.product_release}" pattern="yyyy-MM-dd"/></div>
            <div><strong>AS책임자 전화번호:</strong> ${product.product_as_manager_phone}</div>
            <div><strong>제품 종류:</strong> ${product.product_type}</div>
            <div><strong>제품 색상:</strong> ${product.product_color}</div>
        </div>
        
        <!-- 리뷰 섹션 -->
        <div id="review-section" class="review-section">
        	<div class="review-header">
        		<h3 class="review-title">리뷰</h3>
        	<button class="review-write-btn" onclick="reviewwrite()">리뷰 작성하기</button>
        	</div>
        	
        	<!-- 테이블 상속 해서 사용 -->
        	<!-- 리뷰는 별도의 상속 테이블을 따로 만들어서 사용한다. -->
        	<!-- 리뷰 목록 (forEach로 반복) -->
        	<c:forEach var="review" items="${review_list}" varStatus="status">
        		<div class="review-item">
        			<div class="review-content-area">
        				<div class="review-top">
        					<h4 class="review-item-title">${review.review_title}</h4>
        					<span class="review-author">작성자: ${review.user_nickname != null ? review.user_nickname : sessionScope.user_id}</span>
        				</div>
        				<div class="review-bottom">
        					<div class="review-text">
        						<p>${review.review_content != null ? review.review_content : '정말 좋은 상품입니다! 배송도 빠르고 품질도 만족스럽습니다.'}</p>
        					</div>
        				</div>
        			</div>
        		</div>
        	</c:forEach>
        	
        	
        	
        	<!-- 샘플 리뷰 (reviews가 없을 경우) -->
        	<c:if test="${empty reviews}">
        		<div class="review-item">
        			<div class="review-content-area">
        				<div class="review-top">
        					<h4 class="review-item-title">만족스러운 구매</h4>
        					<span class="review-author">작성자: 구매고객1</span>
        				</div>
        				<div class="review-bottom">
        					<div class="review-image">
        						<img src="https://via.placeholder.com/150x150/e0e0e0/666?text=Review+Image" alt="리뷰 이미지" />
        					</div>
        					<div class="review-text">
        						<p>정말 좋은 상품입니다! 배송도 빠르고 품질도 만족스럽습니다. 가격 대비 성능이 우수하네요.</p>
        					</div>
        				</div>
        			</div>
        		</div>
        		
        		<div class="review-item">
        			<div class="review-content-area">
        				<div class="review-top">
        					<h4 class="review-item-title">추천합니다</h4>
        					<span class="review-author">작성자: 구매고객2</span>
        				</div>
        				<div class="review-bottom">
        					<div class="review-image">
        						<img src="https://via.placeholder.com/150x150/f0f0f0/888?text=Review+Photo" alt="리뷰 이미지" />
        					</div>
        					<div class="review-text">
        						<p>색상도 예쁘고 크기도 딱 맞습니다. 조립도 쉽고 견고해요. 다음에도 이 브랜드 제품을 구매할 예정입니다.</p>
        					</div>
        				</div>
        			</div>
        		</div>
        	</c:if>
        </div>
        
        <!-- 상품 문의 -->
        <!-- 상품 문의 섹션 (리뷰 섹션 아래에 추가) -->
		<div id="inquiry-section" class="inquiry-section">
		    <div class="inquiry-header">
		        <h3 class="inquiry-title">상품 문의</h3>
		        <button class="inquiry-write-btn" onclick="inquirywrite()">문의 작성하기</button>
		    </div>
		    
		    
		    <!-- 문의 목록 -->
		    <c:forEach var="inquiry" items="${product_inquiry_list}" varStatus="status">
		        <!-- ✅ 중요: data-user-id와 data-inquiry-id 속성 추가 -->
		        <div class="inquiry-item" data-user-id="${inquiry.user_id}" data-inquiry-id="${inquiry.pinquiry_id}">
		            <div class="inquiry-content-area">
		                <div class="inquiry-top">
		                    <div class="inquiry-meta">
		                        <span class="inquiry-author">작성자: ${inquiry.user_nickname != null ? inquiry.user_nickname : '익명'}</span>
		                        <span class="inquiry-date">
		                            <fmt:formatDate value="${inquiry.pinquiry_date}" pattern="yyyy-MM-dd HH:mm"/>
		                        </span>
		                        <p>${inquiry.pinquiry_status}</p>  
		                       	 <!-- 이거 문의하는데 시간이 필요하네 -->
		                        <!-- 보다 중요한건 카카오페이결제인데 -->
		                        
		                        <!-- JavaScript로 제어될 내 문의 영역 -->
		                        <div class="my-inquiry-section" style="display: none;">
		                            <p class="my-inquiry-text">내 문의: ${inquiry.user_id}</p>
		                        </div>
		                    </div>
		                    
		                    <button><a href="product_reply_view?product_id=${inquiry.product_id }&pinquiry_id=${inquiry.pinquiry_id }"> 답변달기</a></button>
		                    <!-- 만약 user의 roles가 관리자이면은 답변달기 버튼이 활성화 -->
		                    <c:set var="userRole" value="${sessionScope.user_id }"/>
		                    <c:if test="${sessionScope.user_id }"></c:if>

		   
		                    <!-- JavaScript로 제어될 버튼 영역 (오른쪽에 위치) -->
		                    <div class="inquiry-actions" style="display: none;">
		                        <button class="btn-edit" onclick="location.href='product_inquiry_update_view?pinquiry_id=${inquiry.pinquiry_id}'">수정</button>
		                        <button class="btn-delete" onclick="location.href='product_inquiry_delete?pinquiry_id=${inquiry.pinquiry_id}&user_id=${inquiry.user_id}&product_id=${inquiry.product_id }'">삭제</button>
		                    </div>
		                </div>
		                
		                <div class="inquiry-content">
		                    <!-- 문의 내용 -->
		                    <p class="inquiry-text">${inquiry.pinquiry_content != null ? inquiry.pinquiry_content : '문의 내용이 없습니다.'}</p>
		                </div>
		                
		                <!-- 답변 영역 -->
		                <c:if test="${inquiry.preply_content != null and inquiry.preply_content != ''}">
		                    <div class="inquiry-answer">
		                        <div class="answer-header">
		                            <span class="answer-label">📋 관리자 답변</span>
		                            <span class="answer-date">
		                                <fmt:formatDate value="${inquiry.preply_date}" pattern="yyyy-MM-dd HH:mm"/>
		                            </span>
		                        </div>
		                        <div class="answer-content">
		                            <p>${inquiry.preply_content}</p>
		                        </div>
		                    </div>
		                </c:if>
		            </div>
		        </div>
		    </c:forEach>
		    
		    <!-- 문의가 없을 경우 -->
		    <c:if test="${empty product_inquiry_list}">
		        <div class="no-inquiry">
		            <div class="no-inquiry-content">
		                <p>💬 아직 등록된 문의가 없습니다.</p>
		                <p>상품에 대해 궁금한 점이 있으시면 문의를 남겨주세요.</p>
		            </div>
		        </div>
		    </c:if>
		</div>
		
		
		
		
		<!-- 배송/환불 섹션 -->
		<h3>배송/환불</h3>
		<div class="deliver-refund-section">
			
			<div id="deliver-section" class="deliver-section">
				<p>배송</p>
				<p>배송: 일반택배</p>
				<p>배송비:3000원</p>
				<p>도서산간 추가 배송비: 3000원</p>
				<p>배송불가 지역:	배송불가 지역이 없습니다.</p>
			</div>
			
			<div class="exchange-section"> 
				<p>교환</p>
				<p>반품배송비:	3,000원 (최초 배송비가 무료인 경우 6,000원 부과)</p>
				<p>교환배송비: 6000원</p>
				<p>보내실곳: 구트아카데미</p>
				
			</div>
			
			
			<div class="deliver-info">
				<p>반품/교환 사유에 따른 요청 가능 기간 <br />
					반품 시 먼저 판매자와 연락하셔서 반품사유, 택배사, 배송비, 반품지 주소 등을 협의하신 후 반품상품을 발송해 주시기 바랍니다. <br />
					<br />
					1.구매자 단순 변심은 상품 수령 후 7일 이내 (구매자 반품배송비 부담) <br />
					2.표시/광고와 상이, 계약내용과 다르게 이행된 경우 상품 수령 후 3개월 이내, 그 사실을 안 날 또는 알 수 있었던 날로부터 30일 이내. <br />
					둘 중 하나 경과 시 반품/교환 불가 (판매자 반품배송비 부담) <br />
					반품/교환 불가능 사유 <br />
					아래와 같은 경우 반품/교환이 불가능합니다. <br />
					 <br />
					1.반품요청기간이 지난 경우 <br />
					2.구매자의 책임 있는 사유로 상품 등이 멸실 또는 훼손된 경우 (단, 상품의 내용을 확인하기 위하여 포장 등을 훼손한 경우는 제외) <br />
					3.포장을 개봉하였으나 포장이 훼손되어 상품가치가 현저히 상실된 경우 (예: 식품, 화장품) <br />
					4.구매자의 사용 또는 일부 소비에 의하여 상품의 가치가 현저히 감소한 경우 (라벨이 떨어진 의류 또는 태그가 떨어진 명품관 상품인 경우) <br />
					5.시간의 경과에 의하여 재판매가 곤란할 정도로 상품 등의 가치가 현저히 감소한 경우 (예: 식품, 화장품) <br />
					6.고객주문 확인 후 상품제작에 들어가는 주문제작상품 <br />
					7.복제가 가능한 상품 등의 포장을 훼손한 경우 (CD/DVD/GAME/도서의 경우 포장 개봉 시)</p>
			
			</div>
			
		</div>
		
    </div>
    

    <!-- 오른쪽 섹션: 상품 정보 및 구매 옵션 -->
    <div class="right-section">
        <p class="mallname">${product.product_mall_name}</p>
        <h1 class="title">${product.product_name}</h1>
		<!-- 상품 정보는 product_id를 전달한다. -->
		<!-- 바로주문은 product_id를 전달한다. -->
		
		<!-- 장바구니 담기는 product_id와 user_id를 반환한다 -->
			
		<!--  장바구니 담기와 바로담기 모두 product_id를 반환하니까  -->
		
		
		<!--  그러면 장바구니 담기와 바로구매가 별반 차이가 없는것 아닌가
		
		<-- 장바구니 담기와 바로구매 차이가 뭐지 파라미터로 전달  -->
		
        <!-- 할인 정보 -->
        <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
 
        <c:choose>
            <c:when test="${hasDiscount}">
                <!-- 할인이 있는 경우 -->
                <c:set var="discountPercent" value="${product.product_discountrate * 100}" />
                <c:set var="discountAmount" value="${product.product_price * product.product_discountrate}" />
                <c:set var="salePrice" value="${product.product_price - discountAmount}" />

                <p class="discountrate"><fmt:formatNumber value="${discountPercent}" pattern="#"/>% 할인</p>
                <p class="original-price">정가: ₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></p>
                <p class="sale-price">할인가: ₩<fmt:formatNumber value="${salePrice}" pattern="#,###"/></p>
            </c:when>
            <c:otherwise>
                <!-- 할인이 없는 경우 -->
                <p class="sale-price">가격: ₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></p>
            </c:otherwise>
        </c:choose>

        <!-- 수량 선택 -->
        <div class="quantity-controls">
            <span>수량:</span>
            <button type="button" onclick="changeCount(-1)">-</button>
            <span id="count">1</span>
            <button type="button" onclick="changeCount(1)">+</button>
        </div>

        <!-- 구매 버튼들 -->
        <div class="action-buttons">
            <button class="cart-btn" onclick="showAlert()">장바구니 담기</button>
            <button class="order-btn" onclick="goorder()">바로 주문하기</button>
        </div>
      
    </div>
</div>

</body>
</html>