package com.ama.don.shop.service;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopWriteService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopWriteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map=model.asMap();
		MultipartHttpServletRequest mtfRequest=
				(MultipartHttpServletRequest) map.get("request");
		
//		//String product_id=mtfRequest.getParameter("product_id");
//		
//		//iDao.product_list(product_id);
//		String pcategoryid=mtfRequest.getParameter("pcategory");
//		String pname=mtfRequest.getParameter("pname");
//		String pprice=mtfRequest.getParameter("pprice");
//		String pdiscountrate=mtfRequest.getParameter("pdiscountrate");
//		String pmall_name=mtfRequest.getParameter("pmall_name");
//		
//		//
//		//String pimg=mtfRequest.getParameter("file");
//		//
//		String pmadein=mtfRequest.getParameter("pmadein");
//		String prelease=mtfRequest.getParameter("prelease");
//		String pasmanager_phone=mtfRequest.getParameter("pasmanager_phone");
//		String ptype=mtfRequest.getParameter("ptype");
//		String pcolor=mtfRequest.getParameter("pcolor");
//		String pistoday=mtfRequest.getParameter("pistoday");
		
		//map
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("category_id", Integer.parseInt(mtfRequest.getParameter("pcategory")));
		paramMap.put("product_name", mtfRequest.getParameter("pname"));
		paramMap.put("product_price", Integer.parseInt(mtfRequest.getParameter("pprice")));
		paramMap.put("product_discountrate", Float.parseFloat(mtfRequest.getParameter("pdiscountrate")));
		paramMap.put("product_mall_name", mtfRequest.getParameter("pmall_name"));
		paramMap.put("product_madein", mtfRequest.getParameter("pmadein"));
	    paramMap.put("product_release", mtfRequest.getParameter("prelease"));
	    paramMap.put("product_as_manager_phone", mtfRequest.getParameter("pasmanager_phone"));
		paramMap.put("product_type", mtfRequest.getParameter("ptype"));
		paramMap.put("product_color", mtfRequest.getParameter("pcolor"));
	    paramMap.put("product_istoday", mtfRequest.getParameter("pistoday"));
		
		//product insert
	    iDao.write(paramMap);
	    
	    int pid = ((BigInteger) paramMap.get("productId")).intValue();
	    //int pid=(int) paramMap.get("productId");
	    
		String workPath=System.getProperty("user.dir");
		System.out.println(workPath);
		
	
		String root=workPath+"\\src\\main\\resources\\static\\uploads\\shop";
		List<MultipartFile> fileList=mtfRequest.getFiles("file");
		
		System.out.println("업로드된 파일 수: " + fileList.size());
		for (MultipartFile mf : fileList) {
		    System.out.println("파일명: " + mf.getOriginalFilename());
		}
		
		
		//product_img insert
		long longtime=System.currentTimeMillis();
		for(int i= 0; i<fileList.size(); i++) {
			MultipartFile mf = fileList.get(i);
			String originalFile=mf.getOriginalFilename();
			System.out.println("original files : "+originalFile);
			//long longtime=System.currentTimeMillis();
			String changeFile=longtime+"_"+originalFile;
			System.out.println("change files :"+changeFile);
			
			String pathfile=root+"\\"+changeFile;
			try {
				//mf가 null이 아니면
				if(mf !=null &&!originalFile.equals("")) {
			
					mf.transferTo(new File(pathfile));
					System.out.println("upload success~~");
					
					if(i==0) {
						//첫파일
						iDao.imgwritemain(pid,changeFile);
					}else {
						//db기록
						iDao.imgwrite(pid,changeFile);
					}
					System.out.println("rebrdimgtb write sucess");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
//		//product_img insert
//		for(MultipartFile mf:fileList) {
//			String originalFile=mf.getOriginalFilename();
//			System.out.println("original files : "+originalFile);
//			long longtime=System.currentTimeMillis();
//			String changeFile=longtime+"_"+originalFile;
//			System.out.println("change files :"+changeFile);
//			
//			String pathfile=root+"\\"+changeFile;
//			try {
//				if(!originalFile.equals("")) {
//					mf.transferTo(new File(pathfile));
//					System.out.println("upload success~~");
//					
//					//db기록
//					iDao.imgwrite(changeFile);
//					System.out.println("rebrdimgtb write sucess");
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
		
	}
	
}
