package com.ama.don.shop.service.Kakaopay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.KakaoPayReadyRequest;
import com.ama.don.shop.dto.KakaoPayReadyResponse;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentRequest;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Service
public class ShopKakaopayService implements ShopServiceinter {
	
	@Autowired
	private RestTemplate restTemplate;

	private final String KAKAO_PAY_HOST = "https://kapi.kakao.com";
	
	private final String KAKAO_PAY_READY_URL = "/v1/payment/ready";
	
	private final String KAKAO_PAY_APPROVE_URL = "/v1/payment/approve";

	private final String ADMIN_KEY = " DEVEF6C54B19E28F78A792F15C6570541ECCC9CC"; // 카카오 개발자센터에서 발급

	private String savedTid; // 클래스 필드로 추가

	HttpSession session = null;

	@Autowired
	private ShopIDao iDao;

	public ShopKakaopayService(ShopIDao iDao) {
		this.iDao = iDao;
		this.restTemplate = new RestTemplate(); // 🔧 이 줄 추가
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		System.out.println("정상동작1");
		
		// 🔧 이 부분 추가!
	    if (this.restTemplate == null) {
	        System.out.println("RestTemplate이 null이므로 직접 생성합니다.");
	        this.restTemplate = new RestTemplate();
	    }
		
		String order_id = request.getParameter("order_id");
		Long orderid = Long.parseLong(order_id);

		LoginMemberService loginMemberService = new LoginMemberService();
		MemberDto memberDto = loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember", memberDto);

		Long userid = memberDto.getUser_id();

		// 주문 정보
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

		// 임시 주문번호
		String temporderid = "TEMP_" + System.currentTimeMillis();

		// 단일 주문

		// 1. 주문 정보 담기
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

		// 주문 정보를 임시저장
		model.addAttribute("orderinfo", orderFlatDto);

		String orderType = request.getParameter("orderType");

		// 주문 처리 결과 변수들
		int total = 0;
		List<Orders_productsDto> orderProducts = new ArrayList<>();
		boolean shouldClearCart = false;

		// 2. 주문 상품 담기
		// 주문에 상품을 검색하는데 (장바구니,상품 상세)에서 가져와야한다.
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

			// 상품 정보 조회 (검증용)
			ProductFlatDto product = iDao.product(productId);
			if (product == null) {
				throw new IllegalArgumentException("존재하지 않는 상품입니다.");
			}
			
			// 주문 상품 정보 생성
			orderFlatDto.setProduct_id(productId);
			orderFlatDto.setProduct_name(product.getProduct_name());
			orderFlatDto.setOp_quantity(qty);
			orderFlatDto.setOp_price(product.getDiscountedPrice());
			orderFlatDto.setOp_totalprice(total);

		} else {
			// === 장바구니 주문 ===
			System.out.println("=== 장바구니 주문 처리 ===");

			ArrayList<CartFlatDto> cartFlatDtos = iDao.cart_list_flat(userid);

			if (cartFlatDtos == null || cartFlatDtos.isEmpty()) {
				throw new IllegalArgumentException("장바구니가 비어있습니다.");
			}

			total = 0; // 초기화
			int totalqty = 0;
			String itemName;

			// 첫번쨰 상품 이름 가져오기
			CartFlatDto flatDto = cartFlatDtos.get(0);
			itemName = flatDto.getProduct_name() + "외" + (cartFlatDtos.size() - 1) + "건";

			// 총가격하고 수량을 찾기
			for (CartFlatDto cartitem : cartFlatDtos) {
				total += cartitem.getTotalPrice();

				totalqty += cartitem.getCart_quantity();

			}

			orderFlatDto.setProduct_name(itemName);
			orderFlatDto.setOp_quantity(totalqty);
			orderFlatDto.setOp_totalprice(total);

			
			shouldClearCart = true; // 장바구니 주문은 클리어함

			System.out.println("장바구니 주문 - 상품 개수: " + orderProducts.size() + ", 총액: " + total);
		}

		
		
		System.out.println("정상동작2");
		
		// 4. 주문 상품 임시 저장
		model.addAttribute("orderFlatDto", orderFlatDto);
		model.addAttribute("orderProducts", orderProducts);

		// 5. 카카오페이 결재 테스트용 주문 번호
		String kpay_orderid = "tcon" + System.currentTimeMillis();

		// 🔧 수정 3: 카카오페이 결제 준비 호출 수정
		KakaoPayReadyResponse readyResponse = kakaoPayReady(kpay_orderid, String.valueOf(userid),
				orderFlatDto.getProduct_name(), orderFlatDto.getOp_quantity(), total);

		// 🔧 수정 4: TID 저장 (실제 구현 필요)
		this.savedTid = readyResponse.getTid();

		// 세션에 저장하거나 DB에 저장
        HttpSession session = request.getSession();
        session.setAttribute("kakaotid", readyResponse.getTid());
        session.setAttribute("kpay_orderid", kpay_orderid);
        session.setAttribute("orderFlatDto", orderFlatDto);
        session.setAttribute("orderType", orderType);          // 🔧 추가: orderType 세션 저장
        session.setAttribute("userId", userid);                // 🔧 추가: userId 세션 저장
        session.setAttribute("originalTotal", total); // 🔧 추가: 원래 금액 저장

        // 6. 모델에 리다이렉트 URL 추가
        model.addAttribute("redirecturl", readyResponse.getNext_redirect_pc_url());
        model.addAttribute("orderDto", orderFlatDto);
        model.addAttribute("paymentReady", true); // 🔧 추가: 결제 준비 완료 플래그
		
		/*
		 * // 카카오페이 결제 준비 요청 데이터 설정 KakaoPayReadyRequest kakaorequest = new
		 * KakaoPayReadyRequest();
		 * kakaorequest.setPartner_order_id(String.valueOf(kpay_orderid)); // 주문번호
		 * kakaorequest.setPartner_user_id(userid); // 사용자 이름
		 * kakaorequest.setItem_name(orderFlatDto.getProduct_name()); // 주문 상품명 조합
		 * kakaorequest.setQuantity(orderFlatDto.getOp_quantity()); // 전체 상품 수량
		 * kakaorequest.setTotal_amount(orderFlatDto.getOrder_totalprice()); // 주문 총 가격
		 * kakaorequest.setVat_amount(orderFlatDto.getOrder_totalprice() / 10); // 부가세
		 * 10% kakaorequest.setApproval_url("http://localhost:8080/payment/success");
		 * kakaorequest.setCancel_url("http://localhost:8080/payment/cancel");
		 * kakaorequest.setFail_url("http://localhost:8080/payment/fail");
		 * 
		 * // 6. HTTP 헤더 설정 HttpHeaders headers = new HttpHeaders();
		 * headers.set("Authorization", "KakaoAK " + KakaoPayConfig.ADMIN_KEY);
		 * headers.set("Content-Type",
		 * "application/x-www-form-urlencoded;charset=utf-8");
		 * 
		 * // 요청 파라미터를 URL 인코딩 형식으로 변환 MultiValueMap<String, String> params = new
		 * LinkedMultiValueMap<>(); params.add("cid", kakaorequest.getCid());
		 * params.add("partner_order_id", kakaorequest.getPartner_order_id());
		 * params.add("partner_user_id",
		 * String.valueOf(kakaorequest.getPartner_user_id())); params.add("item_name",
		 * kakaorequest.getItem_name()); params.add("quantity",
		 * String.valueOf(kakaorequest.getQuantity())); params.add("total_amount",
		 * String.valueOf(kakaorequest.getTotal_amount())); params.add("vat_amount",
		 * String.valueOf(kakaorequest.getVat_amount())); params.add("tax_free_amount",
		 * String.valueOf(kakaorequest.getTax_free_amount()));
		 * params.add("approval_url", kakaorequest.getApproval_url());
		 * params.add("fail_url", kakaorequest.getFail_url()); params.add("cancel_url",
		 * kakaorequest.getCancel_url());
		 * 
		 * HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,
		 * headers);
		 * 
		 * 
		 * // try { ResponseEntity<KakaoPayReadyResponse> response =
		 * restTemplate.exchange( KAKAO_PAY_HOST + KAKAO_PAY_READY_URL, HttpMethod.POST,
		 * entity, KakaoPayReadyResponse.class );
		 * 
		 * return response.getBody();
		 * 
		 * } catch (Exception e) { log.error("카카오페이 결제 준비 API 호출 실패: {}",
		 * e.getMessage(), e); throw new RuntimeException("카카오페이 결제 준비 중 오류가 발생했습니다.");
		 * }
		 */

		/*
		 * // try { ResponseEntity<KakaoPayReadyResponse> response =
		 * restTemplate.exchange( KakaoPayConfig.KAKAO_PAY_HOST +
		 * KakaoPayConfig.KAKAO_PAY_READY_URL, HttpMethod.POST, entity,
		 * KakaoPayReadyResponse.class);
		 * 
		 * KakaoPayReadyResponse kakaoreadyResponse = response.getBody();
		 * 
		 * // TID를 세션이나 DB에 저장 (결제 승인 시 필요) saveTidForOrder(order_id,
		 * readyResponse.getTid());
		 * 
		 * return;
		 * 
		 * } catch (Exception e) { log.error("카카오페이 결제 준비 실패: {}", e.getMessage());
		 * throw new RuntimeException("카카오페이 결제 준비 중 오류가 발생했습니다."); }
		 */

		//
		/*
		 * // 5. 카카오 페이 결제 준비 호출 KakaoPayReadyResponse readyResponse
		 * 
		 * String itemName= getitemName(orderProducts);
		 * 
		 * 
		 * public String getitemName;
		 * 
		 * 
		 * //주문 상품은 여러개이거나 한개이다. for(Orders_productsDto orders_productsDto
		 * :orderProducts) {
		 * 
		 * long op_product_id=orders_productsDto.getProduct_id(); ProductFlatDto
		 * productFlatDto=iDao.product(op_product_id); String
		 * product_name=productFlatDto.getProduct_name();
		 * 
		 * 
		 * }
		 */

		// 6. 결제

		/*
		 * // 3.세션에 정보 임시 저장 session.setAttribute("orderFlatDto", orderFlatDto);
		 * 
		 * 
		 * // 4. ✅ 카카오페이 결제 준비 호출 (매개변수 수정) KakaoPayReadyResponse readyResponse =
		 * kakaoPayReady( temporderid, user_id, getItemName(orderFlatDto),
		 * orderFlatDto.getOp_totalprice() );
		 * 
		 * //5. tid를 세션에 저장 session.setAttribute("kakaotid", readyResponse.getTid());
		 * session.setAttribute("temporderid", temporderid); //임시주문 생성
		 * 
		 * 
		 * //6.모델에 리다이렉트url을 추가
		 * model.addAttribute("redirecturl",readyResponse.getNext_redirect_pc_url());
		 * model.addAttribute("orderDto",orderFlatDto);
		 * 
		 */

		// 주문을 찾아서 주문을 받는 리스트

	}

	// 🔧 수정 5: 카카오페이 결제 준비 메서드 완전 수정
	public KakaoPayReadyResponse kakaoPayReady(String orderId, String userId, String itemName, int quantity,
			int amount) {
		
		System.out.println("정상동작3");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", orderId);
		params.add("partner_user_id", userId);
		params.add("item_name", itemName);
		params.add("quantity", String.valueOf(quantity));
		params.add("total_amount", String.valueOf(amount));
		params.add("vat_amount", String.valueOf(amount / 10)); // 부가세 10%
		params.add("tax_free_amount", "0");
		// 🔧 수정 1: 주소 변경 - payment 컨트롤러와 일치시키기
        params.add("approval_url", "http://localhost:8505/shop/kakaopaysuccess"); // 🔧 변경
        params.add("cancel_url", "http://localhost:8505/shop/kakaopaycancel");    // 🔧 변경
        params.add("fail_url", "http://localhost:8505/shop/kakaopayfail");        // 🔧 변경

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

		try {
			System.out.println("정상동작4");
			ResponseEntity<KakaoPayReadyResponse> response = restTemplate.exchange(KAKAO_PAY_HOST + KAKAO_PAY_READY_URL,
					HttpMethod.POST, entity, KakaoPayReadyResponse.class);

			return response.getBody();

		} catch (Exception e) {
			System.out.println("카카오페이 결제 준비 API 호출 실패"+e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("카카오페이 결제 준비 중 오류가 발생했습니다.");
		}
	}

	// 🔧 수정 6: 카카오페이 승인 메서드 수정
	public KakaoPayApprovalResponse kakaoPayApprove(String pgToken, String orderId, String userId) {

		// 🔧 이 부분 추가!
	    if (this.restTemplate == null) {
	        System.out.println("RestTemplate이 null이므로 직접 생성합니다.");
	        this.restTemplate = new RestTemplate();
	    }
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", savedTid); // 저장된 tid 사용
		params.add("partner_order_id", orderId);
		params.add("partner_user_id", userId);
		params.add("pg_token", pgToken);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

		try {
			ResponseEntity<KakaoPayApprovalResponse> response = restTemplate.exchange(
					KAKAO_PAY_HOST + KAKAO_PAY_APPROVE_URL, HttpMethod.POST, entity, KakaoPayApprovalResponse.class);

			return response.getBody();

		} catch (Exception e) {
			System.out.printf("카카오페이 결제 승인 API 호출 실패: {}", e.getMessage(), e);
			throw new RuntimeException("카카오페이 결제 승인 중 오류가 발생했습니다.");
		}
	}
}
