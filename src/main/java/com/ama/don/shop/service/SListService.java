package com.ama.don.shop.service;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.IDao;

import jakarta.servlet.http.HttpServletRequest;

public class SListService implements SServiceinter{

	private IDao iDao;
	public SListService(IDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		
		iDao.product_list(product_id);
		
	}

}
