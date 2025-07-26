package com.ama.don.shop.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.ProductDto;

@Mapper
public interface IDao {
	public void imgwritemain(int pid, String firstFile);
	public void imgwrite(int pid, String changeFile);
	public int write(Map<String, Object> map);
	public ArrayList<ProductDto> product_list();
	public ProductDto product(String product_id);
	public void cart_write(int user_id,int product_id,int cart_quantity);
	public ArrayList<CartDto> cart_list(String user_id);

	
}
