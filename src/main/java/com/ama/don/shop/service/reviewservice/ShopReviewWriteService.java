package com.ama.don.shop.service.reviewservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentDto;
import com.ama.don.shop.dto.PaymentResult;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ShopReviewWriteService implements ShopServiceinter{
	

    private ShopIDao iDao;;
    public ShopReviewWriteService(ShopIDao iDao) {
        this.iDao=iDao;
    }
    
    @Override
    public void execute(Model model) {
        
        Map<String, Object> map=model.asMap();
        MultipartHttpServletRequest request=
				(MultipartHttpServletRequest) map.get("request");
		
		
		String workPath=System.getProperty("user.dir");
		System.out.println(workPath);
	
		
		//사용자 폼 정보
        String user_id=request.getParameter("user_id");
        String product_id=request.getParameter("product_id");
        String review_title=request.getParameter("review_title");
		String review_content=request.getParameter("review_content");
		
		
		System.out.println("user_id: "+user_id);
		System.out.println("product_id: "+product_id);
		System.out.println("review_title: "+review_title);
		System.out.println("review_content: "+review_content);

		
		
		//
		Long userid=Long.parseLong(user_id);
		Long productid=Long.parseLong(product_id);
		
		ShopReviewFlatDto shopReviewFlatDto=new ShopReviewFlatDto();
		shopReviewFlatDto.setUser_id(userid);
		shopReviewFlatDto.setTarget_id(productid);
		shopReviewFlatDto.setReview_title(review_title);
		shopReviewFlatDto.setReview_content(review_content);
		

		try {
			
			iDao.review_write(shopReviewFlatDto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
    }
}
