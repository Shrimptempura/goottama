package com.ama.don.shop.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.ama.don.shop.dao.IDao;

import jakarta.servlet.http.HttpServletRequest;

public class SWriteService implements SServiceinter{

	private IDao iDao;
	public SWriteService(IDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
//		
//		Map<String, Object> map=model.asMap();
//		MultipartHttpServletRequest mtfRequest=
//				(MultipartHttpServletRequest) map.get("request");
//		
//		String product_id=mtfRequest.getParameter("product_id");
//		
//		//iDao.product_list(product_id);
//		String pname=mtfRequest.getParameter("pname");
//		String pprice=mtfRequest.getParameter("pprice");
//		String pdiscountrate=mtfRequest.getParameter("pdiscountrate");
//		//
//		String pimg=mtfRequest.getParameter("pimg");
//		//
//		String pmall_name=mtfRequest.getParameter("pmall_name");
//		String pmadein=mtfRequest.getParameter("pmadein");
//		String prelease=mtfRequest.getParameter("prelease");
//		String pasmanager_phone=mtfRequest.getParameter("pasmanager_phone");
//		String ptype=mtfRequest.getParameter("ptype");
//		String pcolor=mtfRequest.getParameter("pcolor");
//		String pistoday=mtfRequest.getParameter("pistoday");
//		
//		
//		iDao.write(pname, pprice, pdiscountrate,pmall_name,pmadein,prelease
//				,pasmanager_phone,ptype,pcolor,pistoday);
//		
//		String workPath=System.getProperty("user.dir");
//		System.out.println(workPath);
//		
//	
//		String root=workPath+"\\src\\main\\resources\\static\\uploads\\shop";
//		List<MultipartFile> fileList=mtfRequest.getFiles("file");
//			
//		//
//		int bid=iDao.selBid();
//		System.out.println("bid: "+bid);
//		
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
//					//db기록
//					iDao.imgwrite(bid,originalFile,changeFile);
//					System.out.println("rebrdimgtb write sucess");
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
		//
	}
	
}
