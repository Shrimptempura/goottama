package com.ama.don.shop.service.Kakaopay;


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
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.KakaoPayReadyResponse;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.PaymentRequest;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Service
public class ShopKakaopayService implements ShopServiceinter{
	
	private final String KAKAO_PAY_HOST = "https://kapi.kakao.com";
	
	private final String ADMIN_KEY = "your_kakao_admin_key"; // 카카오 개발자센터에서 발급
	
	private String savedTid; // 클래스 필드로 추가
	
	HttpSession session=null;

	private ShopIDao iDao;
	
	public ShopKakaopayService(ShopIDao idao) {
		this.iDao=iDao;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub

        Map<String, Object> map=model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
       
        
        String order_id=request.getParameter("order_id");
        Long orderid=Long.parseLong(order_id);
		String user_id=request.getParameter("user_id");
        
        
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
      
        
        OrderFlatDto orderFlatDto=new OrderFlatDto();
        orderFlatDto.setOrderName(order_name);
        orderFlatDto.setOrderEmail(order_email);
        orderFlatDto.setOrderPhone(order_phone);
        orderFlatDto.setDeliver_name(order_deliver_name);
        orderFlatDto.setDeliver_person(order_receiver_name);
        orderFlatDto.setDeliver_loc(order_loc);
        orderFlatDto.setDeliver_detail_loc(order_detailloc);
        orderFlatDto.setDeliver_request(order_request);
        
        //주문 정보를 임시저장
        model.addAttribute("orderinfo",orderFlatDto);
        
        
        String orderType=request.getParameter("orderType");
        
        //단일 상품 주문
        if("direct".equals(orderType)) {
        	
        	String product_id=request.getParameter("product_id");
        	String product_quantity=request.getParameter("product_quantity");
        	
        	Long productid=Long.parseLong("product_id");
        	int productquantity=Integer.parseInt("product_quantity");
        	
        	orderFlatDto.setProduct_id(productid);
        	orderFlatDto.setOp_quantity(productquantity);
        	
        }
        
        
        
        
        
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
	
		
	
	
	
	//request
	
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
	        ResponseEntity<KakaoPayReadyResponse> response = restTemplate.postForEntity(
	                "https://kapi.kakao.com/v1/payment/ready", request, KakaoPayReadyResponse.class
	        );

	        return response.getBody();
	    }
		
		
		//response
		
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
		    ResponseEntity<KakaoPayApprovalResponse> response = restTemplate.postForEntity(
		            "https://kapi.kakao.com/v1/payment/approve", request, KakaoPayApprovalResponse.class
		    );

		    return response.getBody();
		}
		
		
}
