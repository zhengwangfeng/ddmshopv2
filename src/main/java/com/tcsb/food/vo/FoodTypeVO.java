package com.tcsb.food.vo;

import java.util.List;

import com.tcsb.shop.entity.TcsbShopEntity;


public class FoodTypeVO {
	private String id;
	private String name;
	private Integer orders;
	private List<FoodVO> list;
	
	
	public String getId() {
		return id;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FoodVO> getList() {
		return list;
	}
	public void setList(List<FoodVO> list) {
		this.list = list;
	}

}
