package com.ama.don.shop.service.Kakaopay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.KakaoPayReadyResponse;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class ShopKakaopayService implements ShopServiceinter {
	
	@Autowired
	private RestTemplate restTemplate;

	// ✅ Config에서 값 가져오기
	private final String KAKAO_PAY_HOST = KakaoPayConfig.KAKAO_PAY_HOST;
	private final String KAKAO_PAY_READY_URL = KakaoPayConfig.KAKAO_PAY_READY_URL;
	private final String KAKAO_PAY_APPROVE_URL = KakaoPayConfig.KAKAO_PAY_APPROVE_URL;
	private final String SECRET_KEY = KakaoPayConfig.SECRET_KEY;

	private String savedTid; // 클래스 필드로 추가

	@Autowired
	private ShopIDao iDao;

	public ShopKakaopayService(ShopIDao iDao) {
		this.iDao = iDao;
	}

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		System.out.println("=== ShopKakaopayService 실행 시작 ===");
		
		// RestTemplate null 체크
		if (this.restTemplate == null) {
			System.out.println("RestTemplate이 null이므로 직접 생성합니다.");
			this.restTemplate = new RestTemplate();
		}
		
		try {
			// 로그인 사용자 정보 가져오기
			LoginMemberService loginMemberService = new LoginMemberService();
			MemberDto memberDto = loginMemberService.getCurrentLoginMemberDto();
			
			if (memberDto == null) {
				throw new RuntimeException("로그인 정보를 찾을 수 없습니다.");
			}
			
			model.addAttribute("loginMember", memberDto);
			Long userid = memberDto.getUser_id();
			
			System.out.println("현재 사용자 ID: " + userid);

			// 주문 정보 수집
			String order_name = request.getParameter("order_name");
			String order_email = request.getParameter("order_email");
			String order_phone = request.getParameter("order_phone");
			String order_deliver_name = request.getParameter("order_deliver_name");
			String order_receiver_name = request.getParameter("order_receiver_name");
			String order_receiver_tel = request.getParameter("order_receiver_tel");
			String order_zipcode = request.getParameter("order_zipcode");
			String order_loc = request.getParameter("order_loc");
			String order_detailloc = request.getParameter("order_detailloc");
			String order_request = request.getParameter("order_request");
			String orderType = request.getParameter("orderType");

			// 주문 정보 DTO 생성
			OrderFlatDto orderFlatDto = new OrderFlatDto();
			orderFlatDto.setOrderName(order_name);
			orderFlatDto.setOrderEmail(order_email);
			orderFlatDto.setOrderPhone(order_phone);
			orderFlatDto.setDeliver_name(order_deliver_name);
			orderFlatDto.setDeliver_person(order_receiver_name);
			orderFlatDto.setDeliver_recipient_phone(order_receiver_tel);
			orderFlatDto.setDeliver_loc(order_loc);
			orderFlatDto.setDeliver_detail_loc(order_detailloc);
			orderFlatDto.setDeliver_request(order_request);

			// 주문 처리 변수
			int total = 0;
			String itemName = "";
			int totalQuantity = 0;

			// 주문 타입별 처리
			if ("direct".equals(orderType)) {
				// === 바로 주문하기 ===
				System.out.println("=== 바로 주문하기 처리 ===");

				String product_id = request.getParameter("product_id");
				String quantity = request.getParameter("quantity");
				String totalAmount = request.getParameter("totalAmount");

				if (product_id == null || quantity == null || totalAmount == null) {
					throw new IllegalArgumentException("바로 주문에 필요한 파라미터가 누락되었습니다.");
				}

				Long productId = Long.parseLong(product_id);
				int qty = Integer.parseInt(quantity);
				total = Integer.parseInt(totalAmount);

				// 상품 정보 조회
				ProductFlatDto product = iDao.product(productId);
				if (product == null) {
					throw new IllegalArgumentException("존재하지 않는 상품입니다.");
				}
				
				// 주문 상품 정보 설정
				orderFlatDto.setProduct_id(productId);
				orderFlatDto.setProduct_name(product.getProduct_name());
				orderFlatDto.setOp_quantity(qty);
				orderFlatDto.setOp_price(product.getDiscountedPrice());
				orderFlatDto.setOp_totalprice(total);
				
				itemName = product.getProduct_name();
				totalQuantity = qty;

			} else {
				// === 장바구니 주문 ===
				System.out.println("=== 장바구니 주문 처리 ===");

				ArrayList<CartFlatDto> cartFlatDtos = iDao.cart_list_flat(userid);

				if (cartFlatDtos == null || cartFlatDtos.isEmpty()) {
					throw new IllegalArgumentException("장바구니가 비어있습니다.");
				}

				total = 0;
				totalQuantity = 0;

				// 첫번째 상품 이름 기반으로 아이템명 생성
				CartFlatDto firstItem = cartFlatDtos.get(0);
				if (cartFlatDtos.size() > 1) {
					itemName = firstItem.getProduct_name() + " 외 " + (cartFlatDtos.size() - 1) + "건";
				} else {
					itemName = firstItem.getProduct_name();
				}

				// 총가격과 수량 계산
				for (CartFlatDto cartItem : cartFlatDtos) {
					total += cartItem.getTotalPrice();
					totalQuantity += cartItem.getCart_quantity();
				}

				orderFlatDto.setProduct_name(itemName);
				orderFlatDto.setOp_quantity(totalQuantity);
				orderFlatDto.setOp_totalprice(total);

				System.out.println("장바구니 주문 - 상품 개수: " + cartFlatDtos.size() + ", 총액: " + total);
			}

			System.out.println("주문 처리 완료 - 상품명: " + itemName + ", 총액: " + total);

			// 카카오페이 주문번호 생성
			String kpay_orderid = "KPAY_" + System.currentTimeMillis();

			// ✅ 카카오페이 결제 준비 호출
			System.out.println("=== 카카오페이 결제 준비 호출 ===");
			KakaoPayReadyResponse readyResponse = kakaoPayReady(
				kpay_orderid, 
				String.valueOf(userid),
				itemName, 
				totalQuantity, 
				total
			);

			if (readyResponse != null && readyResponse.getTid() != null) {
				System.out.println("✅ 카카오페이 결제 준비 성공!");
				System.out.println("TID: " + readyResponse.getTid());
				System.out.println("리다이렉트 URL: " + readyResponse.getNext_redirect_pc_url());

				// TID 저장
				this.savedTid = readyResponse.getTid();

				// 세션에 저장
				HttpSession session = request.getSession();
				session.setAttribute("kakaotid", readyResponse.getTid());
				session.setAttribute("kpay_orderid", kpay_orderid);
				session.setAttribute("orderFlatDto", orderFlatDto);
				session.setAttribute("orderType", orderType);
				session.setAttribute("userId", userid);
				session.setAttribute("originalTotal", total);

				// 모델에 리다이렉트 URL 추가
				model.addAttribute("redirecturl", readyResponse.getNext_redirect_pc_url());
				model.addAttribute("orderDto", orderFlatDto);
				model.addAttribute("paymentReady", true);

			} else {
				System.out.println("❌ 카카오페이 응답이 null이거나 TID가 없습니다.");
				model.addAttribute("error", "카카오페이 결제 준비 실패");
				model.addAttribute("paymentReady", false); // ✅ 실패 시 false
			}

		} catch (Exception e) {
			System.out.println("❌ execute 실행 중 오류: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "결제 준비 중 오류: " + e.getMessage());
		}
	}

	// ✅ 카카오페이 결제 준비 메인 메서드
	public KakaoPayReadyResponse kakaoPayReady(String orderId, String userId, String itemName, int quantity, int amount) {
		
		System.out.println("=== 카카오페이 결제 준비 시작 ===");
		
		// 공식 API 시도
		try {
			System.out.println("\n🔄 공식 카카오페이 API 시도");
			return kakaoPayReadyOfficial(orderId, userId, itemName, quantity, amount);
		} catch (Exception e) {
			System.out.println("❌ 공식 API 실패: " + e.getMessage());
		}
		
		// 기존 방식 시도 (fallback)
		try {
			System.out.println("\n🔄 기존 방식 시도");
			return kakaoPayReadyLegacy(orderId, userId, itemName, quantity, amount);
		} catch (Exception e) {
			System.out.println("❌ 기존 방식도 실패: " + e.getMessage());
		}
		
		throw new RuntimeException("모든 카카오페이 API 호출 방법이 실패했습니다.");
	}

	// ✅ 공식 카카오페이 API 호출 (JSON 방식)
	private KakaoPayReadyResponse kakaoPayReadyOfficial(String orderId, String userId, String itemName, int quantity, int amount) {
		
		System.out.println("=== 공식 카카오페이 API 호출 ===");
		
		// JSON 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "SECRET_KEY " + SECRET_KEY);
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "application/json");
		
		// JSON 데이터 생성
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("cid", "TC0ONETIME");
		requestBody.put("partner_order_id", orderId);
		requestBody.put("partner_user_id", userId);
		requestBody.put("item_name", itemName);
		requestBody.put("quantity", String.valueOf(quantity));
		requestBody.put("total_amount", String.valueOf(amount));
		requestBody.put("vat_amount", String.valueOf(amount / 10));
		requestBody.put("tax_free_amount", "0");
		requestBody.put("approval_url", "http://localhost:8505/shop/kakaopaysuccess");
		requestBody.put("cancel_url", "http://localhost:8505/shop/kakaopaycancel");
		requestBody.put("fail_url", "http://localhost:8505/shop/kakaopayfail");
		
		System.out.println("=== JSON 요청 데이터 ===");
		requestBody.forEach((key, value) -> {
			System.out.println(key + ": " + value);
		});
		
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
		
		try {
			String fullUrl = KAKAO_PAY_HOST + KAKAO_PAY_READY_URL;
			System.out.println("호출 URL: " + fullUrl);
			
			ResponseEntity<KakaoPayReadyResponse> response = restTemplate.exchange(
				fullUrl,
				HttpMethod.POST,
				entity,
				KakaoPayReadyResponse.class
			);
			
			KakaoPayReadyResponse responseBody = response.getBody();
			
			String tid=responseBody.getTid();
			String payurl=responseBody.getNext_redirect_pc_url();

			
			if (responseBody != null && responseBody.getTid() != null) {
				System.out.println("✅ 공식 API 호출 성공!");
				System.out.println("TID: " + responseBody.getTid());
				System.out.println("PC URL: " + responseBody.getNext_redirect_pc_url());
				return responseBody;
			} else {
				throw new RuntimeException("응답이 비어있습니다.");
			}
	
			
		} catch (HttpClientErrorException e) {
			System.out.println("❌ 공식 API 호출 실패:");
			System.out.println("상태코드: " + e.getStatusCode());
			System.out.println("응답본문: " + e.getResponseBodyAsString());
			throw new RuntimeException("카카오페이 공식 API 호출 실패: " + e.getMessage());
			
		} catch (Exception e) {
			System.out.println("❌ 예상치 못한 오류: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("카카오페이 API 오류: " + e.getMessage());
		}
	}

	// ✅ 기존 방식 API 호출 (Form 데이터 방식)
	private KakaoPayReadyResponse kakaoPayReadyLegacy(String orderId, String userId, String itemName, int quantity, int amount) {
		
		System.out.println("=== 기존 방식 API 호출 ===");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + SECRET_KEY);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", orderId);
		params.add("partner_user_id", userId);
		params.add("item_name", itemName);
		params.add("quantity", String.valueOf(quantity));
		params.add("total_amount", String.valueOf(amount));
		params.add("vat_amount", String.valueOf(amount / 10));
		params.add("tax_free_amount", "0");
		params.add("approval_url", "http://localhost:8505/shop/kakaopaysuccess");
		params.add("cancel_url", "http://localhost:8505/shop/kakaopaycancel");
		params.add("fail_url", "http://localhost:8505/shop/kakaopayfail");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
		
		try {
			// 기존 URL로 시도
			return restTemplate.postForObject(
				"https://kapi.kakao.com/v1/payment/ready",
				entity,
				KakaoPayReadyResponse.class
			);
		} catch (Exception e) {
			System.out.println("❌ 기존 방식 실패: " + e.getMessage());
			throw e;
		}
	}

	// ✅ 카카오페이 승인 메서드 (공식 API 방식으로 수정)
	public KakaoPayApprovalResponse kakaoPayApproveWithTid(String pgToken, String orderId, String userId, String tid) {

	    System.out.println("=== 카카오페이 결제 승인 시작 (TID 직접 전달) ===");
	    System.out.println("전달받은 TID: " + tid);
	    
	    if (this.restTemplate == null) {
	        System.out.println("RestTemplate이 null이므로 직접 생성합니다.");
	        this.restTemplate = new RestTemplate();
	    }

	    // 공식 API 방식으로 시도
	    try {
	        System.out.println("\n🔄 공식 카카오페이 승인 API 시도 (TID: " + tid + ")");
	        return kakaoPayApproveOfficialWithTid(pgToken, orderId, userId, tid);
	    } catch (Exception e) {
	        System.out.println("❌ 공식 승인 API 실패: " + e.getMessage());
	    }
	    
	    // 기존 방식으로 fallback
	    try {
	        System.out.println("\n🔄 기존 방식 승인 API 시도 (TID: " + tid + ")");
	        return kakaoPayApproveLegacyWithTid(pgToken, orderId, userId, tid);
	    } catch (Exception e) {
	        System.out.println("❌ 기존 방식 승인도 실패: " + e.getMessage());
	    }
	    
	    throw new RuntimeException("모든 카카오페이 승인 API 호출 방법이 실패했습니다.");
	}

	// ✅ 공식 카카오페이 승인 API 호출 (JSON 방식, TID 직접 전달)
	private KakaoPayApprovalResponse kakaoPayApproveOfficialWithTid(String pgToken, String orderId, String userId, String tid) {
	    
	    System.out.println("=== 공식 카카오페이 승인 API 호출 (TID 직접 전달) ===");
	    
	    // JSON 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "SECRET_KEY " + SECRET_KEY);
	    headers.add("Content-Type", "application/json");
	    headers.add("Accept", "application/json");
	    
	    // JSON 데이터 생성
	    Map<String, Object> requestBody = new HashMap<>();
	    requestBody.put("cid", "TC0ONETIME");
	    requestBody.put("tid", tid);  // ← 직접 전달받은 TID 사용
	    requestBody.put("partner_order_id", orderId);
	    requestBody.put("partner_user_id", userId);
	    requestBody.put("pg_token", pgToken);
	    
	    System.out.println("=== JSON 승인 요청 데이터 ===");
	    requestBody.forEach((key, value) -> {
	        System.out.println(key + ": " + value);
	    });
	    
	    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
	    
	    try {
	        String fullUrl = KAKAO_PAY_HOST + KAKAO_PAY_APPROVE_URL;
	        System.out.println("승인 호출 URL: " + fullUrl);
	        System.out.println("사용할 TID: " + tid);
	        
	        ResponseEntity<KakaoPayApprovalResponse> response = restTemplate.exchange(
	            fullUrl,
	            HttpMethod.POST,
	            entity,
	            KakaoPayApprovalResponse.class
	        );
	        
	        KakaoPayApprovalResponse approvalResponse = response.getBody();
	        
	        if (approvalResponse != null) {
	            System.out.println("✅ 공식 승인 API 호출 성공!");
	            System.out.println("결제 고유번호(TID): " + approvalResponse.getTid());
	            System.out.println("결제 승인 시간: " + approvalResponse.getApproved_at());
	            System.out.println("결제 금액: " + approvalResponse.getAmount().getTotal());
	            return approvalResponse;
	        } else {
	            throw new RuntimeException("승인 응답이 비어있습니다.");
	        }
	        
	    } catch (HttpClientErrorException e) {
	        System.out.println("❌ 공식 승인 API 호출 실패:");
	        System.out.println("상태코드: " + e.getStatusCode());
	        System.out.println("응답본문: " + e.getResponseBodyAsString());
	        throw new RuntimeException("카카오페이 공식 승인 API 호출 실패: " + e.getMessage());
	        
	    } catch (Exception e) {
	        System.out.println("❌ 예상치 못한 승인 오류: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("카카오페이 승인 API 오류: " + e.getMessage());
	    }
	}

	// ✅ 기존 방식 승인 API 호출 (Form 데이터 방식, TID 직접 전달) - Fallback용
	private KakaoPayApprovalResponse kakaoPayApproveLegacyWithTid(String pgToken, String orderId, String userId, String tid) {
	    
	    System.out.println("=== 기존 방식 승인 API 호출 (TID 직접 전달) ===");
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "KakaoAK " + SECRET_KEY);
	    headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("cid", "TC0ONETIME");
	    params.add("tid", tid);  // ← 직접 전달받은 TID 사용
	    params.add("partner_order_id", orderId);
	    params.add("partner_user_id", userId);
	    params.add("pg_token", pgToken);

	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

	    try {
	        String approveUrl = "https://kapi.kakao.com/v1/payment/approve";
	        System.out.println("기존 방식 승인 요청 URL: " + approveUrl);
	        System.out.println("TID: " + tid);
	        
	        ResponseEntity<KakaoPayApprovalResponse> response = restTemplate.exchange(
	            approveUrl, 
	            HttpMethod.POST, 
	            entity, 
	            KakaoPayApprovalResponse.class
	        );

	        KakaoPayApprovalResponse approvalResponse = response.getBody();
	        
	        if (approvalResponse != null) {
	            System.out.println("✅ 기존 방식 승인 성공!");
	            return approvalResponse;
	        } else {
	            throw new RuntimeException("승인 응답이 비어있습니다.");
	        }

	    } catch (HttpClientErrorException e) {
	        System.out.println("❌ 기존 방식 승인 실패:");
	        System.out.println("상태코드: " + e.getStatusCode());
	        System.out.println("응답본문: " + e.getResponseBodyAsString());
	        throw new RuntimeException("카카오페이 기존 방식 승인 실패: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("❌ 예상치 못한 승인 오류: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("카카오페이 결제 승인 중 오류가 발생했습니다.");
	    }
	}
}