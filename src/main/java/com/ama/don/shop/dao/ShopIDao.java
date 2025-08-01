package com.ama.don.shop.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentDto;
import com.ama.don.shop.dto.ProductDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;

@Mapper
public interface ShopIDao {
	
	// Write iDao
	public void imgwritemain(int pid, String firstFile);
	public void imgwrite(int pid, String changeFile);
	public int write(Map<String, Object> map);
	
	//Product iDao
	public ArrayList<ProductFlatDto> product_list();		//전체 상품 리스트
	public ProductFlatDto product(Long product_id);	//단일 상품 정보
	public ArrayList<ProductFlatDto> productimgs(Long product_id);	//단일 상품의 이미지 리시트
	
	//home에 사용하는 product 일부 조회
	public ArrayList<ProductFlatDto> product_popular_flat_list();
	public ArrayList<ProductFlatDto> product_high_sales_flat_list();	
	
	//home에 사용하는 product 전부 조회
	public ArrayList<ProductFlatDto> product_popular_all_list();
	public ArrayList<ProductFlatDto> product_high_sales_all_list();
	//상품 카테고리로 특정 상품 리스트 조회
	public ArrayList<ProductFlatDto> product_category(Long category);
	
	
	
	
	
	
	//Cart iDao
	public void cart_write(long user_id,long product_id,long product_quantity);
	public ArrayList<CartFlatDto> cart_list_flat(Long user_id);
    public void cart_clear(Long user_id);
    public void cart_delete_item(Long user_id,Long product_id);
    public void cart_update(Long cart_id, int cart_quantity);
    
    
    //
    
    
	//Order iDao
    
    public void order_write(OrdersDto ordersDto);
	public void deliver_write(DeliverDto deliverDto);
	public void orders_products_write(Orders_productsDto orders_productsDto);
	public void payment_write(PaymentDto paymentDto);
	// 주문 조회 관련
	/*
	 * public OrderFlatDto order_flat(Long order_id); // 주문 기본 정보
	 */    
	public ArrayList<OrderFlatDto> order_products_flat(Long order_id); // 주문 상품 목록		//주문 아이디를 받아서 주문 상품들을 조회
    public OrderFlatDto order_detail_flat(Long order_id);             // 주문 전체 정보 (배송/결제 포함) //주문 아이디를 받아서 조회하는 주문
	public ArrayList<OrderFlatDto> user_orders_list(Long userid);	//주문 목록 사용자가 주문한 상품들 
	/*
	 * public ArrayList<OrderFlatDto> user_orders_simple(Long userid); //디버깅 샘플
	 */	//Deliver iDao
	//Deliver iDao
	public void deliver_update(Long order_id, String deliver_person, 
			String deliver_recipient_phone, String deliver_loc, String deliver_detail_loc);
	
	
	
	
	//Review IDao
	public ArrayList<ShopReviewFlatDto> review_list(Long target_id);
	public void review_write(ShopReviewFlatDto shopReviewFlatDto);
	
	
	//User_detail IDao
	public ShopReviewFlatDto user_info(Long user_id);
	

	
	//주문수정을 하는데 실제로는 배송지 수정정도를 할것 그러니까 주문아이디로 배송지를 찾아서 배송지를 deliver_update를 하면된다.
	
}
