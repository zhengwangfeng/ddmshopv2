package com.tcsb.coupon.vo;

public class TcsbUserCouponVO {
	private String shopName;
	private String shopId;
	private Integer fullDiscount;
	private Integer discountDiscount;
	private String expiryDate;
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
	public Integer getFullDiscount() {
		return fullDiscount;
	}
	public void setFullDiscount(Integer fullDiscount) {
		this.fullDiscount = fullDiscount;
	}
	public Integer getDiscountDiscount() {
		return discountDiscount;
	}
	public void setDiscountDiscount(Integer discountDiscount) {
		this.discountDiscount = discountDiscount;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
