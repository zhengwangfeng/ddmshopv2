package com.pc.order.vo;

import java.util.List;

import com.tcsb.orderitem.entity.TcsbOrderItemEntity;

public class OrderItemVo {
	
	private TcsbOrderItemEntity orderItem;

	private List<FoodMealVo> foodMealVo;

	public TcsbOrderItemEntity getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(TcsbOrderItemEntity orderItem) {
		this.orderItem = orderItem;
	}

	public List<FoodMealVo> getFoodMealVo() {
		return foodMealVo;
	}

	public void setFoodMealVo(List<FoodMealVo> foodMealVo) {
		this.foodMealVo = foodMealVo;
	}
}
