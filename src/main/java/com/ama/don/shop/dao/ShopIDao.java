package com.ama.don.shop.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.ProductDto;

@Mapper
public interface ShopIDao {
	
	// Write iDao
	public void imgwritemain(int pid, String firstFile);
	public void imgwrite(int pid, String changeFile);
	public int write(Map<String, Object> map);
	
	//ShopHome iDao
	public ArrayList<ProductDto> product_list();
	public ProductDto product(String product_id);
	
	
	//Cart iDao
	public void cart_write(long user_id,long product_id,long product_quantity);
	public ArrayList<CartFlatDto> cart_list_flat(Long user_id);
	
	
	
}
