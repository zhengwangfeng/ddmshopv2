package com.tcsb.shopcar.VO;

import java.util.List;

public class TcsbShopCarVO {
	private String deskId;
	//我的购物车新加的信息
	private String shopName;
	private Integer shopCarItemsSize;
	private Double totalMoney;
	private String shopImg;
	private Double finalMoney;
	//前后端不匹配的问题
	private List<TcsbShopCarItemVO> tcsbOrderItemVOs;
	public Double getFinalMoney() {
		return finalMoney;
	}
	public void setFinalMoney(Double finalMoney) {
		this.finalMoney = finalMoney;
	}
	public List<TcsbShopCarItemVO> getTcsbOrderItemVOs() {
		return tcsbOrderItemVOs;
	}
	public void setTcsbOrderItemVOs(List<TcsbShopCarItemVO> tcsbOrderItemVOs) {
		this.tcsbOrderItemVOs = tcsbOrderItemVOs;
	}
	public Integer getShopCarItemsSize() {
		return shopCarItemsSize;
	}
	public void setShopCarItemsSize(Integer shopCarItemsSize) {
		this.shopCarItemsSize = shopCarItemsSize;
	}
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	
	public List<TcsbShopCarItemVO> getTcsbShopCarItemVOs() {
		return tcsbShopCarItemVOs;
	}
	public void setTcsbShopCarItemVOs(List<TcsbShopCarItemVO> tcsbShopCarItemVOs) {
		this.tcsbShopCarItemVOs = tcsbShopCarItemVOs;
	}
	private List<TcsbShopCarItemVO> tcsbShopCarItemVOs;
	public String getDeskId() {
		return deskId;
	}
	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}
}
