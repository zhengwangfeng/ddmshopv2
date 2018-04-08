package com.tcsb.shop.VO;

import java.util.List;

public class TcsbShopVO {
	private String userId;
	private List<String> shopFootList;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getShopFootList() {
		return shopFootList;
	}
	public void setShopFootList(List<String> shopFootList) {
		this.shopFootList = shopFootList;
	}
}
