package com.pc.order.vo;

import java.util.List;

import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;

public class SubOrderItemVo {
	
	private TcsbUserOrderItemEntity subOrderItem;
	
	private List<FoodMealVo> foodMealVo;

	

	public List<FoodMealVo> getFoodMealVo() {
		return foodMealVo;
	}

	public void setFoodMealVo(List<FoodMealVo> foodMealVo) {
		this.foodMealVo = foodMealVo;
	}

	public TcsbUserOrderItemEntity getSubOrderItem() {
		return subOrderItem;
	}

	public void setSubOrderItem(TcsbUserOrderItemEntity subOrderItem) {
		this.subOrderItem = subOrderItem;
	}
}
