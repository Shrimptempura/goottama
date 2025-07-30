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
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        
        //
        String user_id=request.getParameter("user_id");
        String product_id=request.getParameter("product_id");
        String review_title=request.getParameter("review_title");
		String review_content=request.getParameter("review_content");
		String review_img=request.getParameter("review_image");
		
		
		System.out.println("user_id: "+user_id);
		System.out.println("product_id: "+product_id);
		System.out.println("review_title: "+review_title);
		System.out.println("review_content: "+review_content);
		System.out.println("리뷰 이미지url: "+review_img);
		
		

        // 파일 파라미터 처리 (올바른 방법)
        String savedFileName = null;
		
		 try {
	            // MultipartHttpServletRequest로 캐스팅
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	            MultipartFile reviewImageFile = multipartRequest.getFile("review_image");
	            
	            System.out.println("=== 파일 정보 ===");
	            if (reviewImageFile != null) {
	                System.out.println("파일명: " + reviewImageFile.getOriginalFilename());
	                System.out.println("파일크기: " + reviewImageFile.getSize());
	                System.out.println("파일 비어있음?: " + reviewImageFile.isEmpty());
	                
	                // 파일이 있으면 저장
	                if (!reviewImageFile.isEmpty()) {
	                    savedFileName = saveImageFile(reviewImageFile);
	                    System.out.println("저장된 파일명: " + savedFileName);
	                } else {
	                    System.out.println("업로드된 파일이 없습니다.");
	                }
	            } else {
	                System.out.println("MultipartFile이 null입니다.");
	            }
	        } catch (ClassCastException e) {
	            System.out.println("MultipartHttpServletRequest 캐스팅 실패: " + e.getMessage());
	            System.out.println("request 타입: " + request.getClass().getName());
	        }
		
	
        //null cheak
      	if(product_id==null) {
      		System.out.println("product_id가 null 입니다.");
      	}
      		
      	//null cheak
      	if(user_id==null) {
      		System.out.println("user_id가 null 입니다.");
      	}
      	
      	
      	
    	Long productid=Long.parseLong(product_id);
		Long userid=Long.parseLong(user_id);
		

		iDao.review_write(userid, productid, review_title, review_content, savedFileName);
		
		
		
		// 사용자 아이디를 가지고 사용자 닉네임을 가져와서 사용한다.
		ShopReviewFlatDto reviewFlatDto=iDao.user_info(userid);
  
        model.addAttribute("user_info",reviewFlatDto);
        model.addAttribute("product",iDao.product(productid));
        
    }
    
    
    
    
    
    
    
    
    /**
     * 이미지 파일을 로컬에 저장하고 저장된 파일명을 반환
     */
    private String saveImageFile(MultipartFile file) {
        try {
            // 업로드 디렉토리 설정 (실제 경로로 수정 필요)
            String uploadDir = "C:/uploads/review/";
            
            // 또는 프로젝트 내 static 폴더 사용
            // String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/review/";
            
            // 디렉토리가 없으면 생성
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                System.out.println("디렉토리 생성: " + created + " - " + uploadDir);
            }
            
            // 원본 파일명과 확장자 추출
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            // UUID를 사용한 고유 파일명 생성
            String uuid = UUID.randomUUID().toString();
            String savedFileName = uuid + fileExtension;
            
            // 파일 저장
            File savedFile = new File(uploadDir + savedFileName);
            file.transferTo(savedFile);
            
            System.out.println("파일 저장 성공: " + savedFile.getAbsolutePath());
            return savedFileName;
            
        } catch (IOException e) {
            System.out.println("파일 저장 실패: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
