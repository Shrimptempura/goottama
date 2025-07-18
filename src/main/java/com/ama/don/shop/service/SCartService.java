package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.IDao;
import com.ama.don.shop.dto.Product_imgDto;

import jakarta.servlet.http.HttpServletRequest;

public class SCartService implements SServiceinter{

	private IDao iDao;
	public SCartService(IDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		
	}

}
