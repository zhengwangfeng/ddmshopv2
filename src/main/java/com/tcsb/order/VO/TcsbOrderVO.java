package com.tcsb.order.VO;

import java.util.Date;
import java.util.List;

public class TcsbOrderVO {
	private String orderNo;
	private Boolean isComment;
	private Integer shopCarItemsSize;
	public Integer getShopCarItemsSize() {
		return shopCarItemsSize;
	}
	public void setShopCarItemsSize(Integer shopCarItemsSize) {
		this.shopCarItemsSize = shopCarItemsSize;
	}
	public Boolean getIsComment() {
		return isComment;
	}
	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	private String userId;
	private String shopId;
	private String deskId;
	private String finalMoney;
	private String totalMoney;
	private String note;
	private String taste;
	private Date createTime;
	private Integer people;
	private String isRoom;
	private String universalCouponPrice;
	private String platformDiscountPrice;
	private String specialCouponPrice;
	private String isJustNowServing;
	
	
	public String getPlatformDiscountPrice() {
		return platformDiscountPrice;
	}
	public void setPlatformDiscountPrice(String platformDiscountPrice) {
		this.platformDiscountPrice = platformDiscountPrice;
	}
	public String getSpecialCouponPrice() {
		return specialCouponPrice;
	}
	public void setSpecialCouponPrice(String specialCouponPrice) {
		this.specialCouponPrice = specialCouponPrice;
	}
	public String getUniversalCouponPrice() {
		return universalCouponPrice;
	}
	public void setUniversalCouponPrice(String universalCouponPrice) {
		this.universalCouponPrice = universalCouponPrice;
	}

	//查看订单详情新增的信息
	private String shopName;
	private String shopImg;
	
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
	public String getFinalMoney() {
		return finalMoney;
	}
	public void setFinalMoney(String finalMoney) {
		this.finalMoney = finalMoney;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDeskId() {
		return deskId;
	}
	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	private List<TcsbOrderItemVO> tcsbOrderItemVOs;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public Integer getPeople() {
		return people;
	}
	public void setPeople(Integer people) {
		this.people = people;
	}
	public String getIsRoom() {
		return isRoom;
	}
	public void setIsRoom(String isRoom) {
		this.isRoom = isRoom;
	}
	public List<TcsbOrderItemVO> getTcsbOrderItemVOs() {
		return tcsbOrderItemVOs;
	}
	public void setTcsbOrderItemVOs(List<TcsbOrderItemVO> tcsbOrderItemVOs) {
		this.tcsbOrderItemVOs = tcsbOrderItemVOs;
	}
	public String getIsJustNowServing() {
		return isJustNowServing;
	}
	public void setIsJustNowServing(String isJustNowServing) {
		this.isJustNowServing = isJustNowServing;
	}
}
