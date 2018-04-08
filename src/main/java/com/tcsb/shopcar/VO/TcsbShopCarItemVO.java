package com.tcsb.shopcar.VO;

public class TcsbShopCarItemVO {
	private String foodId;
	private Integer count;
	//我的购物车要展示的新增信息
	private String foodName;
	
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
