package com.ama.don.shop.service.Kakaopay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
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

	private final String KAKAO_PAY_HOST = "https://kapi.kakao.com";

	private final String ADMIN_KEY = "your_kakao_admin_key"; // 카카오 개발자센터에서 발급

	private String savedTid; // 클래스 필드로 추가

	HttpSession session = null;

	private ShopIDao iDao;

	public ShopKakaopayService(ShopIDao idao) {
		this.iDao = iDao;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub

        Map<String, Object> map=model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
       
        
        String order_id=request.getParameter("order_id");
        Long orderid=Long.parseLong(order_id);
		String user_id=request.getParameter("user_id");
		
		
		if(user_id==null || user_id.isEmpty()) {
			
			
			System.out.println("user_id가 null 입니다.");
		}
		
		Long userid=Long.parseLong(user_id);
        
        
        //주문 정보
        String order_name=request.getParameter("order_name");
        String order_email=request.getParameter("order_email");
        String order_phone=request.getParameter("order_phone");
        
       
        String order_deliver_name=request.getParameter("order_deliver_name");
        String order_receiver_name=request.getParameter("order_receiver_name");
        String order_receiver_tel=request.getParameter("order_receiver_tel");
        
        String order_zipcode=request.getParameter("order_zipcode");
        String order_loc=request.getParameter("order_loc");
        String order_detailloc=request.getParameter("order_detailloc");
        String order_request=request.getParameter("order_request");

        
        //임시 주문번호
        String temporderid = "TEMP_" + System.currentTimeMillis();
        
        //단일 주문
     
        
        
        //1. 주문 정보 담기
        OrderFlatDto orderFlatDto=new OrderFlatDto();
        orderFlatDto.setOrderName(order_name);
        orderFlatDto.setOrderEmail(order_email);
        orderFlatDto.setOrderPhone(order_phone);
        orderFlatDto.setDeliver_name(order_deliver_name);
        orderFlatDto.setDeliver_person(order_receiver_name);
        orderFlatDto.setDeliver_recipient_phone(order_receiver_tel);
        orderFlatDto.setDeliver_loc(order_loc);
        orderFlatDto.setDeliver_detail_loc(order_detailloc);
        orderFlatDto.setDeliver_request(order_request);
        
        //주문 정보를 임시저장
        model.addAttribute("orderinfo",orderFlatDto);
        
        String orderType=request.getParameter("orderType");
        
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
            
            Orders_productsDto orderProduct = new Orders_productsDto();
            orderProduct.setProduct_id(productId);
            orderProduct.setOp_quantity(qty);                           // ✅ 사용자 선택 수량
            orderProduct.setOp_price(product.getDiscountedPrice());     // ✅ 할인된 단가
            orderProduct.setOp_totalprice(total);                       // ✅ 실제 주문 총액
            
            orderProducts.add(orderProduct);
            shouldClearCart = false; // 바로 주문은 장바구니 클리어 안함
            
            System.out.println("바로 주문 - 상품ID: " + productId + ", 수량: " + qty + ", 총액: " + total);
            
        } else {
            // === 장바구니 주문 ===
            System.out.println("=== 장바구니 주문 처리 ===");
            
            ArrayList<CartFlatDto> cartFlatDtos = iDao.cart_list_flat(userid);
            
            if (cartFlatDtos == null || cartFlatDtos.isEmpty()) {
                throw new IllegalArgumentException("장바구니가 비어있습니다.");
            }
            
            total = 0; // 초기화
            int totalqty=0;
            String itemName;
            
            
            
            //첫번쨰 상품 이름 가져오기
            CartFlatDto flatDto=cartFlatDtos.get(0);
            itemName=flatDto.getProduct_name()+"외"+(cartFlatDtos.size()-1)+"건";
            
            for (CartFlatDto cartitem : cartFlatDtos) {
            	total += cartitem.getTotalPrice();
                
                Orders_productsDto orderProduct = new Orders_productsDto();
                orderProduct.setProduct_id(cartitem.getProduct_id());
                orderProduct.setOp_quantity(cartitem.getCart_quantity());     // ✅ 장바구니 수량
                orderProduct.setOp_price(cartitem.getDiscountedPrice());      // ✅ 할인된 단가
                orderProduct.setOp_totalprice(cartitem.getTotalPrice());      // ✅ 아이템별 총액
            
             
                totalqty+=cartitem.getCart_quantity();
                
                
                orderProducts.add(orderProduct);
            }
            
            orderFlatDto.setProduct_name(itemName);
            orderFlatDto.setOp_quantity(totalqty);
            
            shouldClearCart = true; // 장바구니 주문은 클리어함
            
            System.out.println("장바구니 주문 - 상품 개수: " + orderProducts.size() + ", 총액: " + total);
        }
        
        
        orderFlatDto.setOp_totalprice(total);
        
        // 4. 주문 상품 임시 저장
        model.addAttribute("orderProducts",orderProducts);
        
        
        
		/*
		 * // 5. 카카오 페이 결제 준비 호출 KakaoPayReadyResponse readyResponse
		 * 
		 * String itemName= getitemName(orderProducts);
		 * 
		 * 
		 * public String getitemName;
		 * 
		 * 
		 */    //주문 상품은 여러개이거나 한개이다.
        for(Orders_productsDto orders_productsDto :orderProducts) {
        	
        	long op_product_id=orders_productsDto.getProduct_id();
        	ProductFlatDto productFlatDto=iDao.product(op_product_id);
        	String product_name=productFlatDto.getProduct_name();
        	
        	
        }
        
        
        
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
        
        
        //주문을 찾아서 주문을 받는 리스트
        
        
        
        
	}

	// request

	public KakaoPayReadyResponse kakaoPayReady(int amount) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "user123");
		params.add("item_name", "상품명");
		params.add("quantity", "1");
		params.add("total_amount", String.valueOf(amount));
		params.add("tax_free_amount", "0");
		params.add("approval_url", "http://localhost:8080/kakaopay/success");
		params.add("cancel_url", "http://localhost:8080/kakaopay/cancel");
		params.add("fail_url", "http://localhost:8080/kakaopay/fail");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<KakaoPayReadyResponse> response = restTemplate
				.postForEntity("https://kapi.kakao.com/v1/payment/ready", request, KakaoPayReadyResponse.class);

		return response.getBody();
	}

	// response

	public KakaoPayApprovalResponse kakaoPayApprove(String pgToken) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "YOUR_ADMIN_KEY");
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", savedTid); // 1단계에서 저장한 tid
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "user123");
		params.add("pg_token", pgToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<KakaoPayApprovalResponse> response = restTemplate
				.postForEntity("https://kapi.kakao.com/v1/payment/approve", request, KakaoPayApprovalResponse.class);

		return response.getBody();
	}

}
