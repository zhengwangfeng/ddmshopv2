package com.tcsb.salestatistics.vo;

import java.util.Date;

import com.tcsb.salestatistics.entity.TcsbSaleStatisticsEntity;

public class TcsbSaleStatisticsEntityVo extends TcsbSaleStatisticsEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9157428575240371570L;
	
	//商品销售总量
	private String allSaleCount;

	//食物名称
	private String foodName;

	//会员复购数
	private String vipSaleCount;
	//食物上架时间
	private Date onSaleTime;
	//会员昵称
	private String nickName;
	//手机号
	private String mobile;
	//最近消费时间
	private String lastSaleTime;

	private String foodId;


	@Override
	public String getFoodId() {
		return foodId;
	}

	@Override
	public void setFoodId(String foodId) {
		this.foodId = foodId;
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
	public Date getOnSaleTime() {
		return onSaleTime;
	}
	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLastSaleTime() {
		return lastSaleTime;
	}
	public void setLastSaleTime(String lastSaleTime) {
		this.lastSaleTime = lastSaleTime;
	}

	@Override
	public String getFoodName() {
		return foodName;
	}

	@Override
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
}
