package com.tcsb.associatorstatistics.vo;

import java.util.Date;

import com.weixin.weixinuser.entity.WeixinUserEntity;

public class WeixinUserStatiticsVo extends WeixinUserEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5862065654055228971L;
	
	//消费次数
	private int saleCount;
	//均消费金额
	private String avgSaleMoney;
	//上一次消费时间
	private String lastTime;
	//消费总额
	private String allSaleMoney;
	//消费菜品列表
	private String foodList;
	//订单id
	private String orderid;
	//消费时间
	private Date saleTime;
	
	private String deskName;
	

	
	
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}


	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getAvgSaleMoney() {
		return avgSaleMoney;
	}
	public void setAvgSaleMoney(String avgSaleMoney) {
		this.avgSaleMoney = avgSaleMoney;
	}
	public String getAllSaleMoney() {
		return allSaleMoney;
	}
	public void setAllSaleMoney(String allSaleMoney) {
		this.allSaleMoney = allSaleMoney;
	}
	public String getFoodList() {
		return foodList;
	}
	public void setFoodList(String foodList) {
		this.foodList = foodList;
	}

	public Date getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getDeskName() {
		return deskName;
	}
	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}

	
	
}
