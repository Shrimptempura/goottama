package com.ama.don.shop.service.orderservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopOrderDetailService implements ShopServiceinter{
	
	//주문 상세 뷰

	private ShopIDao iDao;
	public ShopOrderDetailService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		Long userid=memberDto.getUser_id();
		
		try {
			System.out.println("=== 주문 조회 시작 ===");
		    System.out.println("조회 user_id: " + userid);
		    
		    // 1.사용자가 등록한 주문들 가져오기 (리스트로)
		    ArrayList<OrderFlatDto> userOrders = iDao.user_orders_list(userid);
		   
		    System.out.println("복잡 조회 결과: " + userOrders.size() + "개");
		    

		    // 각 주문별 상품 목록 조회하여 Map으로 구성
		    Map<Long, ArrayList<OrderFlatDto>> orderProductsMap = new HashMap<>();
		    
		    // 각 주문별 배송 정보를 조회하여 Map으로 구성 
		    Map<Long, OrderFlatDto> orderdeliverMap=new HashMap<>();
		   
		    for(OrderFlatDto order : userOrders) {
		        Long orderId = order.getOrder_id();
		        System.out.println("주문 ID: " + orderId + ", 날짜: " + order.getOrder_date());
		        
		        // 2. 주문별 상품 저장 
		        ArrayList<OrderFlatDto> products = iDao.order_products_flat(orderId);
		        orderProductsMap.put(orderId, products);
		      
		        // 3. 주문 배송 상태 저장
		        OrderFlatDto orderFlatDto=iDao.order_deliver_info(orderId);
		        orderdeliverMap.put(orderId,orderFlatDto);
		        
		        System.out.println("주문 ID " + orderId + "의 상품 개수: " + products.size());
		    }
		    
		    
		   
		    	
		    model.addAttribute("userOrders", userOrders);
		    model.addAttribute("orderProductsMap", orderProductsMap);
		    model.addAttribute("orderDeliverMap",orderdeliverMap);  // 배송 정보 추가
		    model.addAttribute("user_id", userid);

		    System.out.println("=== 최종 결과 ===");
		    System.out.println("사용자 주문 목록 조회 완료: user_id=" + userid + ", 주문 개수=" + userOrders.size());
		    
		} catch (Exception e) {
		    System.out.println("ERROR: 주문 조회 실패 - " + e.getMessage());
		    e.printStackTrace();
		    model.addAttribute("error", "주문 정보를 불러오는 중 오류가 발생했습니다.");
		}
		
	}

}
