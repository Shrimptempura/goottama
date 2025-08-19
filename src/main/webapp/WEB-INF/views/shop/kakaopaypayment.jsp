<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>카카오페이 결제</title>
    <style>
        body {
            font-family: 'Malgun Gothic', sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        
        .payment-container {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            text-align: center;
        }
        
        .kakao-logo {
            width: 80px;
            height: 80px;
            background: #FEE500;
            border-radius: 50%;
            margin: 0 auto 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
            font-weight: bold;
            color: #3C1E1E;
        }
        
        .payment-info {
            margin: 25px 0;
            padding: 25px;
            background: #f9f9f9;
            border-radius: 12px;
            text-align: left;
        }
        
        .payment-info h3 {
            margin-top: 0;
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        
        .info-row {
            display: flex;
            justify-content: space-between;
            margin: 12px 0;
            padding: 8px 0;
            border-bottom: 1px solid #eee;
        }
        
        .info-label {
            font-weight: bold;
            color: #666;
        }
        
        .info-value {
            color: #333;
        }
        
        .total-amount {
            font-size: 18px;
            color: #e74c3c;
            font-weight: bold;
        }
        
        .payment-buttons {
            margin-top: 30px;
        }
        
        .btn {
            padding: 15px 35px;
            margin: 0 10px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
            font-weight: bold;
        }
        
        .btn-kakao {
            background-color: #FEE500;
            color: #3C1E1E;
            box-shadow: 0 3px 10px rgba(254, 229, 0, 0.3);
        }
        
        .btn-kakao:hover {
            background-color: #FFD700;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(254, 229, 0, 0.4);
        }
        
        .btn-cancel {
            background-color: #95a5a6;
            color: white;
        }
        
        .btn-cancel:hover {
            background-color: #7f8c8d;
            transform: translateY(-2px);
        }
        
        .loading {
            display: none;
            margin: 20px 0;
        }
        
        .spinner {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #FEE500;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: 0 auto;
        }
        
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        
        .error-message {
            color: #e74c3c;
            background: #ffeaea;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 5px solid #e74c3c;
        }
        
        .success-message {
            color: #27ae60;
            background: #eafaf1;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 5px solid #27ae60;
        }
        
        .popup-notice {
            background: #e8f4fd;
            color: #2980b9;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            font-size: 14px;
            border-left: 4px solid #3498db;
        }
    </style>
</head>
<body>
    <div class="payment-container">
        <!-- 카카오 로고 -->
        <div class="kakao-logo">
            KakaoPay
        </div>
        
        <h2>카카오페이 간편결제</h2>
        
        <!-- 에러 메시지 표시 -->
        <c:if test="${not empty error}">
            <div class="error-message">
                <strong>⚠️ 결제 오류</strong><br>
                ${error}
            </div>
            <div class="payment-buttons">
                <button type="button" class="btn btn-cancel" onclick="goBack()">
                    이전으로 돌아가기
                </button>
            </div>
        </c:if>
        
        <!-- 결제 준비 성공 시 -->
        <c:if test="${paymentReady and empty error}">
            <div class="success-message">
                <strong>✅ 결제 준비 완료</strong><br>
                카카오페이 결제창이 곧 열립니다.
            </div>
            
            <!-- 결제 정보 표시 -->
            <div class="payment-info">
                <h3>📋 주문 정보</h3>
                <div class="info-row">
                    <span class="info-label">상품명:</span>
                    <span class="info-value">${orderDto.product_name}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">주문자:</span>
                    <span class="info-value">${orderDto.orderName}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">수량:</span>
                    <span class="info-value">${orderDto.op_quantity}개</span>
                </div>
                <div class="info-row">
                    <span class="info-label">결제금액:</span>
                    <span class="info-value total-amount">${orderDto.op_totalprice}원</span>
                </div>
            </div>
            
            <!-- 숨겨진 필드 (JavaScript에서 사용) -->
            <input type="hidden" id="paymentUrl" value="${redirecturl}" />
            <input type="hidden" id="orderId" value="${sessionScope.kpay_orderid}" />
            
            <!-- 팝업 안내 -->
            <div class="popup-notice">
                <strong>💡 안내사항</strong><br>
                • 팝업 차단이 설정되어 있다면 결제창이 열리지 않을 수 있습니다.<br>
                • 팝업 허용 후 다시 시도해주세요.<br>
                • 결제창이 열리지 않으면 아래 버튼을 클릭하세요.
            </div>
            
            <!-- 로딩 표시 -->
            <div class="loading" id="loadingDiv">
                <div class="spinner"></div>
                <p>결제창을 여는 중입니다...</p>
            </div>
            
            <!-- 결제 버튼 -->
            <div class="payment-buttons">
                <button type="button" class="btn btn-kakao" onclick="startKakaoPayment()" id="paymentBtn">
                    💳 카카오페이 결제창 열기
                </button>
                <button type="button" class="btn btn-cancel" onclick="goBack()">
                    ❌ 결제 취소
                </button>
            </div>
        </c:if>
        
        <!-- 결제 준비 실패 시 -->
        <c:if test="${not paymentReady and empty error}">
            <div class="error-message">
                <strong>⚠️ 결제 준비 실패</strong><br>
                결제 준비 과정에서 문제가 발생했습니다.<br>
                잠시 후 다시 시도해주세요.
            </div>
            <div class="payment-buttons">
                <button type="button" class="btn btn-cancel" onclick="goBack()">
                    이전으로 돌아가기
                </button>
            </div>
        </c:if>
    </div>

    <script>
        // 카카오페이 결제 팝업 처리 함수
        function openKakaoPayPopup(paymentUrl, orderId) {
            console.log('🔥 카카오페이 팝업 열기:', paymentUrl);
            
            if (!paymentUrl || paymentUrl.trim() === '') {
                alert('결제 URL이 없습니다. 다시 시도해주세요.');
                return;
            }
            
            // 로딩 표시
            document.getElementById('loadingDiv').style.display = 'block';
            document.getElementById('paymentBtn').disabled = true;
            
            // 팝업 창 설정 (카카오페이에 최적화된 크기)
            const popupOptions = {
                width: 480,
                height: 700,
                left: (window.screen.width / 2) - (480 / 2),
                top: (window.screen.height / 2) - (700 / 2),
                scrollbars: 'yes',
                resizable: 'no',
                toolbar: 'no',
                menubar: 'no',
                location: 'no',
                directories: 'no',
                status: 'no'
            };
            
            const popupFeatures = Object.entries(popupOptions)
                .map(([key, value]) => `${key}=${value}`)
                .join(',');
            
            // 팝업 창 열기
            const popup = window.open(paymentUrl, 'kakaoPayPopup', popupFeatures);
            
            // 로딩 숨기기
            setTimeout(() => {
                document.getElementById('loadingDiv').style.display = 'none';
                document.getElementById('paymentBtn').disabled = false;
            }, 2000);
            
            if (!popup) {
                alert('❌ 팝업이 차단되었습니다!\n\n브라우저 설정에서 팝업을 허용해주세요.\n그 후 "카카오페이 결제창 열기" 버튼을 다시 클릭하세요.');
                document.getElementById('loadingDiv').style.display = 'none';
                document.getElementById('paymentBtn').disabled = false;
                return;
            }
            
            // 팝업 창 포커스
            popup.focus();
            
            // 팝업 창 닫힘 감지 (결제 취소 또는 실패 시)
            const checkClosed = setInterval(() => {
                if (popup.closed) {
                    clearInterval(checkClosed);
                    console.log('카카오페이 팝업이 닫혔습니다.');

                }
            }, 1000);
            
            return popup;
        }

        // 수동으로 결제 버튼 클릭 시 호출할 함수
        function startKakaoPayment() {
            const paymentUrl = document.getElementById('paymentUrl')?.value;
            const orderId = document.getElementById('orderId')?.value;
            
            console.log('결제 시작 - URL:', paymentUrl, 'OrderID:', orderId);
            
            if (!paymentUrl) {
                alert('결제 URL을 가져올 수 없습니다.\n페이지를 새로고침 후 다시 시도해주세요.');
                return;
            }
            
            openKakaoPayPopup(paymentUrl, orderId);
        }

        // 페이지 로드 시 자동으로 카카오페이 팝업 열기
        document.addEventListener('DOMContentLoaded', function() {
            <c:if test="${paymentReady and not empty redirecturl}">
                console.log('✅ 결제 준비 완료 - 자동 팝업 실행');
                
                const paymentUrl = document.getElementById('paymentUrl')?.value;
                const orderId = document.getElementById('orderId')?.value;
                
                if (paymentUrl && paymentUrl.trim() !== '') {
                    // 1.5초 후 자동으로 팝업 열기
                    setTimeout(() => {
                        openKakaoPayPopup(paymentUrl, orderId);
                    }, 1500);
                }
            </c:if>
        });
        
        // 취소 버튼 - 이전 페이지로
        function goBack() {
            if (confirm('정말 결제를 취소하고 이전 페이지로 돌아가시겠습니까?')) {
                history.back();
            }
        }
        
        // === 팝업에서 부모창으로 결과 전달받는 함수들 ===
        
        // 결제 성공 시 부모 창에서 호출할 함수
        window.handlePaymentSuccess = function(data) {
            console.log('🎉 결제 성공:', data);
            
            alert('결제가 완료되었습니다! 주문 내역 페이지로 이동합니다.');
            
            // 성공 페이지로 이동
            if (data && data.redirect) {
                window.location.href = data.redirect;
            } else {
                window.location.href = '/shop/order-success';
            }
        };

        // 결제 실패 시 부모 창에서 호출할 함수
        window.handlePaymentFail = function(data) {
            console.log('❌ 결제 실패:', data);
            alert('결제가 실패했습니다.\n\n' + (data.message || '다시 시도해주세요.'));
            
            // 페이지 새로고침 또는 이전 페이지로
            // window.location.reload();
        };
        
        // 결제 취소 시 부모 창에서 호출할 함수  
        window.handlePaymentCancel = function(data) {
            console.log('🚫 결제 취소:', data);
            
            // 취소 메시지는 표시하지 않고 조용히 처리
            console.log('사용자가 결제를 취소했습니다.');
        };
    </script>
</body>
</html>