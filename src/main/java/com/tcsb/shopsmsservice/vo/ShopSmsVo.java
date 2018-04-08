package com.tcsb.shopsmsservice.vo;

import org.jeecgframework.web.system.pojo.base.TSBaseUser;

public class ShopSmsVo extends TSBaseUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6169337708025541859L;
	
	
	private String shopName;
	
	private String shopId;
	
	private String shopAddress;
	//剩余短信条数
	private String smsCount;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(String smsCount) {
		this.smsCount = smsCount;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	
	

}
