package com.tcsb.settleaccount.vo;

import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;

public class TcsbSettleAccountEntityVo extends TcsbSettleAccountEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String shopName;
	
	private String settleWeek;
	
	private String actualSettleTotal;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSettleWeek() {
		return settleWeek;
	}

	public void setSettleWeek(String settleWeek) {
		this.settleWeek = settleWeek;
	}

	public String getActualSettleTotal() {
		return actualSettleTotal;
	}

	public void setActualSettleTotal(String actualSettleTotal) {
		this.actualSettleTotal = actualSettleTotal;
	}

}
