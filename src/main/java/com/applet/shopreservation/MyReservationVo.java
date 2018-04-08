package com.applet.shopreservation;

import java.util.List;

public class MyReservationVo {
	
	private String reservationid;
	
	
	private String shopName;

	private String shopHeadImg;
	
	private String allMoney;
	
	private String allcount;
	
	private boolean isDeposit;
	
	private int states;
	
	private List<ReServationItemVo> itemVo;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public String getAllcount() {
		return allcount;
	}

	public void setAllcount(String allcount) {
		this.allcount = allcount;
	}

	public List<ReServationItemVo> getItemVo() {
		return itemVo;
	}

	public void setItemVo(List<ReServationItemVo> itemVo) {
		this.itemVo = itemVo;
	}

	public String getShopHeadImg() {
		return shopHeadImg;
	}

	public void setShopHeadImg(String shopHeadImg) {
		this.shopHeadImg = shopHeadImg;
	}

	public boolean isDeposit() {
		return isDeposit;
	}

	public void setDeposit(boolean isDeposit) {
		this.isDeposit = isDeposit;
	}

	public int getStates() {
		return states;
	}

	public void setStates(int states) {
		this.states = states;
	}

	public String getReservationid() {
		return reservationid;
	}

	public void setReservationid(String reservationid) {
		this.reservationid = reservationid;
	}


	
	
}
