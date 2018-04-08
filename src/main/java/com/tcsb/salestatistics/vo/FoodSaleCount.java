package com.tcsb.salestatistics.vo;

public class FoodSaleCount {
	
	
	private String foodid;
	//商品销售总量
	private String allSaleCount;
		//会员复购数
	private String vipSaleCount;
	public String getFoodid() {
		return foodid;
	}
	public void setFoodid(String foodid) {
		this.foodid = foodid;
	}
	public String getAllSaleCount() {
		return allSaleCount;
	}
	public void setAllSaleCount(String allSaleCount) {
		this.allSaleCount = allSaleCount;
	}
	public String getVipSaleCount() {
		return vipSaleCount;
	}
	public void setVipSaleCount(String vipSaleCount) {
		this.vipSaleCount = vipSaleCount;
	}

}
